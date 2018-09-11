package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.SessionError;

public class LogoutException extends SessionException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1695205434408892165L;
	
	public LogoutException(SessionError error, String message) {
		super(error, " invalidation error: " + message);
	}
	
	
}
