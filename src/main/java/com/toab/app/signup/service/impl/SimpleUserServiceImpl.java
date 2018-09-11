package com.toab.app.signup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toab.app.signup.converter.User2UserForm;
import com.toab.app.signup.converter.UserForm2User;
import com.toab.app.signup.entity.User;
import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.exceptions.UserFetchException;
import com.toab.app.signup.exceptions.UserRegistrationException;
import com.toab.app.signup.model.UserRegistrationForm;
import com.toab.app.signup.model.UserVO;
import com.toab.app.signup.repository.UserRepository;
import com.toab.app.signup.service.UserService;

@Component
public class SimpleUserServiceImpl implements UserService {
	
	@Autowired
	private UserForm2User toEntity;
	@Autowired
	private User2UserForm toModel;
	@Autowired
	private UserRepository repository;
	@Autowired
	private MessageService messageService;

	@Override
	public String createUser(UserRegistrationForm userForm) throws UserRegistrationException, ClassNotFoundException {
		try {
			readUser(userForm.getEmail().toString());
			UserError error = UserError.EMAIL_ID_PRESENT;
			String key = error.getKey();
			Object[] args = {userForm.getEmail()};
			String message = messageService.getMessage(key, args);
			throw new UserRegistrationException(error, message);
		} catch (UserFetchException e) {
			User user = toEntity.convert(userForm);
			User saved = repository.save(user);
			return saved.getId().toString();
		}
	}

	/*@Override
	public Boolean isEmailRegistered(String email) {
		BindingResult result = new DirectFieldBindingResult(email, "user.email.id");
		emailVal.validate(email, result);
		if(result.hasErrors()) {
			UserException e = getException(result);
			throw e;
		} else {
			repository.doesUserWithEmailExist(email);
		}
		return null;
	}*/

	@Override
	public List<UserVO> readUsersWithEmail(String email) throws UserFetchException{
		List<User> entities = repository.findByEmailIdIgnoreCaseContaining(email);
		if(entities.isEmpty()) {
			UserError error = UserError.USER_NOT_FOUND;
			String key = error.getKey();
			Object[] args = {"email", email};
			String message = messageService.getMessage(key, args);
			throw new UserFetchException(error, message);
		} else {
			List<UserVO> models = new ArrayList<>();
			for(User u : entities) {
				UserVO vo = toModel.convert(u);
				models.add(vo);
			}
			return models;
		}
	}

	@Override
	public UserVO readUser(Long id) throws UserFetchException {
		User e = repository.getOne(id);
		if(e != null && e.getStatus()) {
			UserVO vo = toModel.convert(e);
			return vo;
		} else {
			UserError error = e == null ? UserError.USER_NOT_FOUND : UserError.USER_INACTIVE;
			Object[] args = new Object[] {"id", id};
			String key = error.getKey();
			String message = messageService.getMessage(key, args);
			throw new UserFetchException(error, message);
		}
	}

	@Override
	public UserVO readUser(String email) throws UserFetchException {
		User e = repository.findByEmailId(email);
		if(e != null && e.getStatus()) {
			UserVO vo = toModel.convert(e);
			return vo;
		} else {
			UserError error = e == null ? UserError.USER_NOT_FOUND : UserError.USER_INACTIVE;
			Object[] args = new Object[] {"email", email};
			String key = error.getKey();
			String message = messageService.getMessage(key, args);
			throw new UserFetchException(error, message);
		}
	}

	@Override
	public Boolean updateUser(Long id, UserRegistrationForm user) throws UserRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateUser(UserRegistrationForm user) throws UserRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deactivateUser(Long id) throws UserRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isUserRegistered(String email) {
		User user = repository.findByEmailId(email);
		return user != null;
	}

	@Override
	public Boolean isUserActive(Long userId) {
		User user = repository.getOne(userId);
		return user != null;
	}

	@Override
	public Boolean isUserActive(String emailId) {
		User user = repository.findByEmailId(emailId);
		if(user != null) {
			return user.getStatus();
		} else {
			return false;
		}
	}

}
