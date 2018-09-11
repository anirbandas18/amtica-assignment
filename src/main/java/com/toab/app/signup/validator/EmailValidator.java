package com.toab.app.signup.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.model.EmailID;

@Component
public class EmailValidator implements Validator {
	
	@Value("${email.id.regex}")
	private String emailIdRegex;
	@Value("${email.domain.regex}")
	private String emailDomainRegex;

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(EmailID.class);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		EmailID eid = (EmailID) arg0;
		String[] tokens = eid.toString().split("@");
		if(tokens.length == 2) {
			EmailID email = new EmailID(tokens[0], tokens[1]);
			if(email.getName() == null || !email.getName().matches(emailIdRegex)) {
				arg1.reject("email id", UserError.EMAIL_ID_NON_COMPLAINT.toString());
			} else if(email.getDomain() == null || !email.getDomain().matches(emailDomainRegex)) {
				arg1.reject("email domain", UserError.EMAIL_DOMAIN_INVALID.toString());
			} 
		} else {
			arg1.reject("email", UserError.EMAIL_ID_INVALID.toString());
		}
	}

}
