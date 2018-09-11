package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.UserError;

public class UserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5737912310681560811L;

	private UserError error;
	
	public UserException(UserError error, String event) {
		super("User " + event);
		this.error = error;
	}
	
	public UserError getError() {
		return this.error;
	}

}
