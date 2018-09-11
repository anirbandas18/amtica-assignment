package com.toab.app.signup.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.model.PhoneNumber;

@Component
public class PhoneValidator implements Validator {
	
	@Value("${phone.country.code.regex}")
	private String countryCodeRegex;
	@Value("${phone.number.regex}")
	private String phoneNumberRegex;

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(PhoneNumber.class);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		PhoneNumber phone = (PhoneNumber) arg0;
		if(phone.getCountryCode() != null && !phone.getCountryCode().matches(countryCodeRegex)) {
			arg1.reject("country code", UserError.COUNTRY_CODE_INVALID.toString());
		} else if(!phone.getNumber().matches(phoneNumberRegex)) {
			arg1.reject("phone number", UserError.PHONE_NUMBER_NON_COMPLAINT.toString());
		} 
	}

}
