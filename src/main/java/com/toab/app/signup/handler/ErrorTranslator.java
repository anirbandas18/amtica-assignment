package com.toab.app.signup.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.toab.app.signup.ErrorMetadata;
import com.toab.app.signup.errors.SessionError;
import com.toab.app.signup.errors.UserError;
import com.toab.app.signup.exceptions.SessionException;
import com.toab.app.signup.exceptions.UserException;
import com.toab.app.signup.model.ErrorResponse;
import com.toab.app.signup.service.impl.MessageService;

@ControllerAdvice
public class ErrorTranslator  {
	
	private List<Class<? extends ErrorMetadata>> listOfErrorTypes;
	
	@Autowired
	private MessageService messageService;
	
	@PostConstruct
	private void init() {
		Reflections reflections = new Reflections("my.project");
		Set<Class<? extends ErrorMetadata>> subTypes = reflections.getSubTypesOf(ErrorMetadata.class);
		listOfErrorTypes = new ArrayList<>(subTypes);
		Comparator<Class<? extends ErrorMetadata>> ERROR_TYPE_CMP = new  Comparator<Class<? extends ErrorMetadata>>() {

			@Override
			public int compare(Class<? extends ErrorMetadata> o1, Class<? extends ErrorMetadata> o2) {
				// TODO Auto-generated method stub
				return o1.getSimpleName().compareTo(o2.getSimpleName());
			}
			
		};
		Collections.sort(listOfErrorTypes, ERROR_TYPE_CMP);
	}
	
	public String parseError(BindingResult result) throws ClassNotFoundException {
		ObjectError error = result.getGlobalError();
		
		//FieldError error = result.getFieldError();
		String name = (String) error.getCode();
		String type = error.getDefaultMessage();
		ErrorMetadata key = getErrorType(type);
		key = key.getByKey(name);
		int size = 1 + error.getArguments().length;
		Object[] args = new Object[size];
		args[0] = result.getTarget();
		for(int i = 1 ; i < size ; i++) {
			args[i] = error.getArguments()[i - 1];
		}
		String message = messageService.getMessage(key.getKey(), args);
		return message;
	}

	private ErrorMetadata getErrorType(String type) throws ClassNotFoundException {
		Class<?> key = Class.forName(type);
		if(key.isAssignableFrom(UserError.class)) {
			return UserError.USER_SYSTEM_ERROR;
		} /*else if(key.isAssignableFrom(LoginError.class)) {
			return LoginError.USER_NOT_ALLOWED;
		} */else if(key.isAssignableFrom(SessionError.class)) {
			return SessionError.COOKIE_INVALID;
		} 
		return null;// can cause untraceable NPE
	}
	
	/*private Class<? extends ErrorMetadata> getErrorType(String type) throws ClassNotFoundException {
		Class<?> key = Class.forName(type);
		int low = 0;
		int high = listOfErrorTypes.size() - 1;
		while(low <= high) {
			int mid = (high + low) / 2;
			Class<? extends ErrorMetadata> i = listOfErrorTypes.get(mid);
			if(i.isAssignableFrom(key)) {
				return i;
			} else if(type.compareTo(i.getClass().getName()) < 0) {
				high = mid - 1;
			} else if(type.compareTo(i.getClass().getName()) > 0) {
				low = mid + 1;
			}
		}
		return null;// can cause untraceable NPE
	}*/
	
	private ResponseEntity<ErrorResponse> parseError(String message, ErrorMetadata error) {
		HttpStatus status = HttpStatus.valueOf(error.getStatus());
		ErrorResponse errorResponse = new ErrorResponse(error.getCode().toString(), message);
		ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(errorResponse, status);
		return response;
	}
	
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
		UserError error = e.getError();
		String message = e.getMessage();
		return parseError(message, error);
		
	}
	
	@ExceptionHandler(value = SessionException.class)
	public ResponseEntity<ErrorResponse> handleSessionException(SessionException e) {
		SessionError error = e.getError();
		String message = e.getMessage();
		return parseError(message, error);
		
	}

}
