package com.toab.app.signup.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.toab.app.signup.entity.AccessToken;
import com.toab.app.signup.entity.WebSession;
import com.toab.app.signup.exceptions.LoginException;
import com.toab.app.signup.handler.AuthenticationFilter;
import com.toab.app.signup.model.Cookie;
import com.toab.app.signup.service.SessionService;

@Component
public class WebSessionRepository implements SessionService {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private AuthenticationFilter authenticator;
	
	@Value("${spring.cache.cache-names}")
	private String cacheName;
	@Value("${spring.cache.redis.time-to-live}")
	private Long lifespanInMilis;

	private static Long sessionId;

	@PostConstruct
	private void init() {
		sessionId = 1000l;
	}

	@Override
	public Cookie createSession(AccessToken token) {
		Cache cache = cacheManager.getCache(cacheName);
		synchronized (WebSessionRepository.class) {
			sessionId++;
		}
		Cookie cookie = new Cookie(sessionId.toString(), String.valueOf(token.hashCode()), token.getUserId().toString());
		WebSession session = new WebSession(sessionId.toString(), token);
		cache.put(cookie.getAccessId(), session);
		System.out.println("Session created: " + cache.get(cookie.getAccessId()));
		return cookie;
	}

	@Override
	public void destroySession(Cookie cookie) throws LoginException, ClassNotFoundException {
		authenticator.checkValidity(cookie);
		Cache cache = cacheManager.getCache(cacheName);
		String key = cookie.getAccessId();
		cache.evict(key);
		synchronized (WebSessionRepository.class) {
			sessionId--;
		}
	}

	/*private SessionError parseSessionError(BindingResult result) {
		ObjectError error = result.getGlobalError();
		String name = error.getCode();
		SessionError e = SessionError.valueOf(name);
		return e;
	}*/

}
