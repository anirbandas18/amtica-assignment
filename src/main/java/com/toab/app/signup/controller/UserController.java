package com.toab.app.signup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.exceptions.UserException;
import com.toab.app.signup.exceptions.UserFetchException;
import com.toab.app.signup.exceptions.UserRegistrationException;
import com.toab.app.signup.handler.ErrorTranslator;
import com.toab.app.signup.model.SuccessResponse;
import com.toab.app.signup.model.UserRegistrationForm;
import com.toab.app.signup.model.UserVO;
import com.toab.app.signup.service.UserService;
import com.toab.app.signup.validator.EmailValidator;
import com.toab.app.signup.validator.UserValidator;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EmailValidator emailValidator;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private ErrorTranslator errorHandler;
	
	@PostMapping
	public ResponseEntity<SuccessResponse> registerUser(@RequestBody UserRegistrationForm userForm) throws UserException {
		BindingResult result = new DirectFieldBindingResult(userForm, "user.registration.form");
		userValidator.validate(userForm, result);
		try {
			if(result.hasErrors()) {
				UserError e = parseUserError(result);
				String message = errorHandler.parseError(result);
				throw new UserRegistrationException(e, message);
			} else {
				String id = userService.createUser(userForm);
				SuccessResponse body = new SuccessResponse("id", id);
				ResponseEntity<SuccessResponse> re = new ResponseEntity<SuccessResponse>(body, HttpStatus.OK);
				return re;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UserException(UserError.USER_SYSTEM_ERROR, e.getMessage());
		}
		
	}
	
	@GetMapping("{emailId}")
	public ResponseEntity<UserVO> fetchUser(@PathVariable("emailId") String emailId) throws UserException {
		BindingResult result = new DirectFieldBindingResult(emailId, "user.emailid");
		emailValidator.validate(emailId, result);
		try {
			if(result.hasErrors()) {
				UserError e = parseUserError(result);
				String message = errorHandler.parseError(result);
				throw new UserFetchException(e, message);
			} else {
				UserVO user = userService.readUser(emailId);
				ResponseEntity<UserVO> re = new ResponseEntity<UserVO>(user, HttpStatus.OK);
				return re;
			}
		} catch (ClassNotFoundException e) {
			throw new UserException(UserError.USER_SYSTEM_ERROR, e.getMessage());
		}
		
	}
	
	private UserError parseUserError(BindingResult result) {
		ObjectError error = result.getGlobalError();
		//String name = error.getDefaultMessage();
		String name = error.getCode();
		UserError e = UserError.valueOf(name);
		return e;
	}

}
