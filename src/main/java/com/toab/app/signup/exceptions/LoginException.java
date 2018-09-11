package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.SessionError;

public class LoginException extends SessionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695205434408892165L;
	
	public LoginException(SessionError error, String message) {
		super(error, " login error: " + message);
	}
	
}
