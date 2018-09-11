package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.SessionError;

public class SessionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695205434408892165L;
	private SessionError error;
	public SessionException(SessionError error, String message) {
		super("Session" + message);
		this.error = error;
	}
	
	public SessionError getError() {
		return this.error;
	}

}
