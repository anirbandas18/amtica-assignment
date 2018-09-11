package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.UserError;

public class UserRegistrationException extends UserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695205434408892165L;
	
	public UserRegistrationException(UserError error, String message) {
		super(error, "registration problem: " + message);
	}
	
	
}
