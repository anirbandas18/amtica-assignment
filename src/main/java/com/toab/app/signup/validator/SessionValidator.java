/*package com.toab.app.signup.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.toab.app.signup.ErrorMetadata;
import com.toab.app.signup.entity.AccessToken;
import com.toab.app.signup.entity.WebSession;
import com.toab.app.signup.errors.SessionError;
import com.toab.app.signup.model.Cookie;
import com.toab.app.signup.service.UserService;

@Component
public class SessionValidator implements Validator {
	
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserService userService;
	
	@Value("${spring.cache.cache-names}")
	private String cacheName;
	@Value("${spring.cache.redis.time-to-live}")
	private Long lifespanInMilis;

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(Cookie.class);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		Cookie cookie = (Cookie) arg0;
		Cache cache = cacheManager.getCache(cacheName);
		WebSession session = (WebSession) cache.get(cookie.getAccessId());
		String type = "";
		ErrorMetadata key = null;
		Object[] errorArgs = new Object[1];
		if(session == null) {
			type = SessionError.class.getName();
			key = SessionError.SESSION_INVALID;
			errorArgs[0] = cookie.getAccessId();
		} else {
			AccessToken token = session.getToken();
			Boolean isActive = userService.isUserActive(token.getUserId());
			if(!isActive) {
				type = SessionError.class.getName();
				key = SessionError.USER_INACTIVE;
				errorArgs[0] = token.getUserId();
			} else {
				Long elapsed = token.getCreatedOn() + lifespanInMilis;
				Date expiryDate = new Date(elapsed);
				Date current = new Date();
				if(expiryDate.before(current)) {
					type = SessionError.class.getName();
					key = SessionError.SESSION_EXPIRED;
					errorArgs[0] = expiryDate;
				}
			}
		}
		arg1.reject(key.toString(), errorArgs, type);
	}

}*/
