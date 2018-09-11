package com.toab.app.signup.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.toab.app.signup.ErrorMetadata;

public enum UserError implements ErrorMetadata {

	USER_INACTIVE(211, "user.inactive", 403),
	EMAIL_ID_PRESENT(212, "email.conflict", 409),
	PHONE_NUMBER_PRESENT(213, "phone.conflict", 409),
	PASSWORD_NON_COMPLAINT(214, "password.weak", 400),
	EMAIL_ID_NON_COMPLAINT(215, "email.non.complaint", 400),
	PHONE_NUMBER_NON_COMPLAINT(216, "phone.non.complaint", 400),
	NAME_NON_COMPLAINT(217, "name.non.complaint", 400),
	COUNTRY_CODE_INVALID(218, "country.code.invalid", 400),
	EMAIL_DOMAIN_INVALID(219, "email.domain.invalid", 400),
	USER_NOT_FOUND(2110, "user.not.found", 404), 
	EMAIL_ID_INVALID(2111,"email.id.invalid", 400),
	USER_SYSTEM_ERROR(21, "user.system.error", 500);
	
	private Integer code;
	private String key;
	private Integer status;
	
	private UserError(Integer code, String key, Integer status) {
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
	
	public List<UserError> getValues() {
		return new ArrayList<>(Arrays.asList(UserError.values()));
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	
}
