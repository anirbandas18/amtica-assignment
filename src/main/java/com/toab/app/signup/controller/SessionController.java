package com.toab.app.signup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toab.app.signup.entity.AccessToken;
import com.toab.app.signup.errors.SessionError;
import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.exceptions.LoginException;
import com.toab.app.signup.exceptions.SessionException;
import com.toab.app.signup.exceptions.UserException;
import com.toab.app.signup.handler.AuthenticationFilter;
import com.toab.app.signup.model.Cookie;
import com.toab.app.signup.model.SuccessResponse;
import com.toab.app.signup.model.UserLoginForm;
import com.toab.app.signup.service.SessionService;
import com.toab.app.signup.service.impl.MessageService;

@RestController
@RequestMapping("session")
public class SessionController {
	
	@Autowired
	private AuthenticationFilter authenticator;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private MessageService messageService;
	
	@PostMapping
	public ResponseEntity<Cookie> doLogin(
			@RequestHeader(value = "Authorization") String credentials, 
			@RequestHeader(value = "X-Forwarded-For") String ip,
			@RequestHeader(value = "User-Agent") String agent) throws UserException, SessionException {
		if(StringUtils.hasText(credentials)) {
			UserLoginForm form = new UserLoginForm();
			form.setCredentials(credentials.toCharArray());
			form.setAgent(agent);
			form.setIp(ip);
			authenticator.validateUser(form);
			AccessToken token = authenticator.generateToken(form);
			Cookie cookie = sessionService.createSession(token);
			ResponseEntity<Cookie> response = new ResponseEntity<Cookie>(cookie, HttpStatus.ACCEPTED);
			return response;
		} else {
			SessionError error = SessionError.CREDENTIALS_INVALID;
			String message = messageService.getMessage(error.getKey(), new Object[] { });
			throw new LoginException(error, message);
		}
	}
	
	@DeleteMapping
	public void doLogout(
			@RequestHeader(value = "AMTICA-SESSION") String sessionId,
			@RequestHeader(value = "AMTICA-ACCESS") String accessId) throws SessionException, UserException {
		Cookie cookie = new Cookie();
		cookie.setAccessId(accessId);
		cookie.setSessionId(sessionId);
		System.out.println(cookie);
		authenticator.checkValidity(cookie);
		try {
			sessionService.destroySession(cookie);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UserException(UserError.USER_SYSTEM_ERROR, e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<SuccessResponse> doValidate(
			@RequestHeader(value = "AMTICA-SESSION") String sessionId,
			@RequestHeader(value = "AMTICA-ACCESS") String accessId) throws SessionException, UserException {
		Cookie cookie = new Cookie();
		cookie.setAccessId(accessId);
		cookie.setSessionId(sessionId);
		System.out.println(cookie);
		authenticator.checkValidity(cookie);
		SuccessResponse body = new SuccessResponse("session", "true");
		ResponseEntity<SuccessResponse> response = new ResponseEntity<SuccessResponse>(body, HttpStatus.OK);
		return response;
	}


}
