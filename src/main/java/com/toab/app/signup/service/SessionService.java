package com.toab.app.signup.service;


import org.springframework.stereotype.Service;

import com.toab.app.signup.entity.AccessToken;
import com.toab.app.signup.exceptions.LoginException;
import com.toab.app.signup.model.Cookie;

@Service
public interface SessionService {

	public Cookie createSession(AccessToken token);
	
	public void destroySession(Cookie cookie) throws LoginException, ClassNotFoundException;

	
}
