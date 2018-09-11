package com.toab.app.signup.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.model.EmailID;
import com.toab.app.signup.model.PhoneNumber;
import com.toab.app.signup.model.UserRegistrationForm;
import com.toab.app.signup.service.PasswordService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private PhoneValidator phoneVal;
	@Autowired
	private EmailValidator emailVal;
	@Autowired
	private PasswordService passServ;

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(UserRegistrationForm.class);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		UserRegistrationForm form = (UserRegistrationForm) arg0;
		UserError key = null;
		Object errorArgs[] = new Object[2];
		if(!StringUtils.hasText(form.getFirstName())) {
			key = UserError.NAME_NON_COMPLAINT;
			errorArgs[0] = "first name";
			errorArgs[1] = form.getFirstName();
		} else if(!StringUtils.hasText(form.getLastName())) {
			key = UserError.NAME_NON_COMPLAINT;
			errorArgs[0] = "last name";
			errorArgs[1] = form.getLastName();
		} else if(!isPasswordComplaint(form.getPassword())) {
			key = UserError.PASSWORD_NON_COMPLAINT;
			errorArgs[0] = "password";
			errorArgs[1] = form.getPassword();
		} else if(!isPhoneValid(form.getPhone(), arg1)) {
			key = UserError.PHONE_NUMBER_NON_COMPLAINT;
			errorArgs[0] = "phone number";
			errorArgs[1] = form.getPhone();
		} else if(!isEmailValid(form.getEmail(), arg1)) {
			key = UserError.EMAIL_ID_NON_COMPLAINT;
			errorArgs[0] = "email id";
			errorArgs[1] = form.getEmail();
		} 
		if(key != null) {
			//arg1.rejectValue(field, key.toString(), errorArgs, UserError.class.getName());
			arg1.reject(key.toString(), errorArgs, UserError.class.getName());
		}
	}
	
	private Boolean isPasswordComplaint(String password) {
		//char[] rawPassword = passServ.demaskPassword(maskedPassword);
		//return passServ.isComplaint(rawPassword);
		String maskedPassword = new String(Base64Utils.decodeFromString(password));
		char[] raw = maskedPassword.toCharArray();
		return passServ.isComplaint(raw);
	}
	
	private Boolean isPhoneValid(PhoneNumber phone, Errors arg1) {
		ValidationUtils.invokeValidator(phoneVal, phone, arg1);
		return !arg1.hasErrors();
	}

	private Boolean isEmailValid(EmailID email, Errors arg1) {
		ValidationUtils.invokeValidator(emailVal, email, arg1);
		return !arg1.hasErrors();
	}
	
}
