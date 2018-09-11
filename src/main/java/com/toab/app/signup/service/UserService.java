package com.toab.app.signup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toab.app.signup.exceptions.UserFetchException;
import com.toab.app.signup.exceptions.UserRegistrationException;
import com.toab.app.signup.model.UserRegistrationForm;
import com.toab.app.signup.model.UserVO;

@Service
public interface UserService {
	
	public String createUser(UserRegistrationForm user) throws UserRegistrationException, ClassNotFoundException;
	
	public List<UserVO> readUsersWithEmail(String email) throws UserFetchException;
	
	public UserVO readUser(Long id) throws UserFetchException;
	
	public UserVO readUser(String email) throws UserFetchException;
	
	public Boolean isUserRegistered(String email);
	
	public Boolean isUserActive(String emailId);
	
	public Boolean isUserActive(Long userId);
	
	// TODO below classes require implementation later as per requirement
	
	public Boolean updateUser(Long id, UserRegistrationForm user) throws UserRegistrationException;
	
	public Boolean updateUser(UserRegistrationForm user) throws UserRegistrationException;
	
	public Boolean deactivateUser(Long id) throws UserRegistrationException;

}
