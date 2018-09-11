package com.toab.app.signup.converter;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.toab.app.signup.entity.User;
import com.toab.app.signup.model.UserRegistrationForm;

@Component
public class UserForm2User implements Converter<UserRegistrationForm, User> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User convert(UserRegistrationForm form) {
		String password = form.getPassword();
		byte[] raw = Base64Utils.decodeFromString(password);
		String secretHash = passwordEncoder.encode(new String(raw));
		User user = new User();
		user.setEmailId(form.getEmail().toString());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setPhoneNumber(form.getPhone().toString());
		user.setSecretHash(secretHash);
		user.setRegisteredOn(new Date(System.currentTimeMillis()));
		return user;
	}

}
