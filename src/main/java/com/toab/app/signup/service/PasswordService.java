package com.toab.app.signup.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswordService {

	public char[] demask(char[] mask);
	
	public char[] mask(char[] raw);
	
	public Boolean isComplaint(char[] password);
	
}
