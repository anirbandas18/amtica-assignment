package com.toab.app.signup.exceptions;

import com.toab.app.signup.errors.UserError;

public class UserFetchException extends UserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3169493239735417888L;

	public UserFetchException(UserError error, String message) {
		super(error, "retrieval error: " + message);
	}
	
}
