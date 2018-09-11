package com.toab.app.signup.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.toab.app.signup.ErrorMetadata;

public enum SessionError implements ErrorMetadata {

	SESSION_INVALID(191, "session.invalid", 400),
	SESSION_EXPIRED(192, "session.expired", 400), 
	COOKIE_INVALID(193, "cookie.invalid", 400), 
	USER_INACTIVE(194, "user.inactive", 400),
	EMAIL_ID_ABSENT(195, "email.not.found", 404),
	PHONE_NUMBER_ABSENT(196, "phone.not.found", 404),
	PASSWORD_INVALID(197, "password.invalid", 400),
	USER_NOT_ALLOWED(198, "user.unauthorized", 401), 
	CREDENTIALS_INVALID(199, "user.credentails.invalid", 400), 
	INALID_IP(1910, "user.invalid.ip", 400),
	INALID_AGENT(1911, "user.invalid.agent", 400);
	
	private Integer code, status;
	private String key;
	
	private SessionError(Integer code, String key, Integer status) {
		this.code = code;
		this.key = key;
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public String getKey() {
		return key;
	}

	@Override
	public List<? extends ErrorMetadata> getValues() {
		return new ArrayList<>(Arrays.asList(SessionError.values()));
	}

	@Override
	public Integer getStatus() {
		return status;
	}
	
}
