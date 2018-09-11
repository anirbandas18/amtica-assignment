package com.toab.app.signup.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import com.toab.app.signup.entity.AccessToken;
import com.toab.app.signup.entity.WebSession;
import com.toab.app.signup.errors.SessionError;
import com.toab.app.signup.exceptions.LoginException;
import com.toab.app.signup.exceptions.UserFetchException;
import com.toab.app.signup.model.Cookie;
import com.toab.app.signup.model.UserLoginForm;
import com.toab.app.signup.model.UserVO;
import com.toab.app.signup.service.UserService;
import com.toab.app.signup.service.impl.MessageService;

@Component
public class AuthenticationFilter {
	
	@Value("${user.ip.regex}")
	private String ipRegex;
	@Value("${spring.cache.cache-names}")
	private String cacheName;
	@Value("${spring.cache.redis.time-to-live}")
	private Long lifespanInMilis;
	@Value("${ip.check.enabled}")
	private Boolean ipCheckEnabled;
	
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void validateUser(UserLoginForm form) throws UserFetchException, LoginException{
		if(!StringUtils.hasText(form.getIp()) || !form.getIp().matches(ipRegex)) {
			// invalid ip
			SessionError error = SessionError.INALID_IP;
			String message = messageService.getMessage(error.getKey(), new Object[] {form.getIp()});
			throw new LoginException(error, message);
		} else if(ipCheckEnabled && !StringUtils.hasText(form.getAgent())) {
			// user agent required
			SessionError error = SessionError.INALID_AGENT;
			String message = messageService.getMessage(error.getKey(), new Object[] {form.getAgent()});
			throw new LoginException(error, message);
		} else {
			String masked = new String(form.getCredentials());
			byte[] raw = Base64Utils.decodeFromString(masked);
			String unmasked = new String(raw);
			String emailId = unmasked.split(":")[0];
			String password = unmasked.split(":")[1];
			if(userService.isUserActive(emailId)) {
				UserVO vo = userService.readUser(emailId);
				if(!passwordEncoder.matches(password, new String(vo.getPassword()))) {
					SessionError error = SessionError.PASSWORD_INVALID;
					String message = messageService.getMessage(error.getKey(), new Object[] {"emailId", emailId});
					throw new LoginException(error, message);
				} else {
					form.setEmail(emailId);
					form.setPassword(password.toCharArray());
				}
			} else {
				// user deactivated
				SessionError error = SessionError.USER_INACTIVE;
				String message = messageService.getMessage(error.getKey(), new Object[] {emailId});
				throw new LoginException(error, message);
			}
		}
	}
	
	public AccessToken generateToken(UserLoginForm form) {
		AccessToken token = new AccessToken();
		try {
			// fetch user details by email id
			UserVO user = userService.readUser(form.getEmail());
			token.setClientAgent(form.getAgent());
			token.setCreatedOn(System.currentTimeMillis());
			token.setIp(form.getIp());
			token.setUserId(user.getId());
		} catch (UserFetchException e) {
			// swallow exception as user validation will be expected to be achieved positively before this
		}
		return token;
	}
	
	public void checkValidity(Cookie cookie) throws LoginException {
		if(!StringUtils.hasText(cookie.getAccessId()) || !StringUtils.hasText(cookie.getSessionId())) {
			// invalid cookie
			SessionError error = SessionError.COOKIE_INVALID;
			String message = messageService.getMessage(error.getKey(), new Object[] {});
			throw new LoginException(error, message);
		} else {
			Cache cache = cacheManager.getCache(cacheName);
			ValueWrapper value = cache.get(cookie.getAccessId());
			if (value == null) {
				SessionError error = SessionError.SESSION_INVALID;
				String message = messageService.getMessage(error.getKey(), new Object[] { cookie.getAccessId() });
				throw new LoginException(error, message);
			} else {
				WebSession session = (WebSession) value.get();
				AccessToken token = session.getToken();
				Boolean isActive = userService.isUserActive(token.getUserId());
				if (!isActive) {
					SessionError error = SessionError.USER_INACTIVE;
					String message = messageService.getMessage(error.getKey(), new Object[] { token.getUserId() });
					throw new LoginException(error, message);
				} else {
					Long elapsed = token.getCreatedOn() + lifespanInMilis;
					Date expiryDate = new Date(elapsed);
					Date current = new Date();
					if (expiryDate.before(current)) {
						SessionError error = SessionError.SESSION_EXPIRED;
						String message = messageService.getMessage(error.getKey(), new Object[] {cookie.getAccessId(), expiryDate });
						throw new LoginException(error, message);
					}
				}
			}
		}
	}

}
