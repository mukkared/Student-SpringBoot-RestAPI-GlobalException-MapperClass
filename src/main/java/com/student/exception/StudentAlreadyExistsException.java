package com.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class StudentAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public StudentAlreadyExistsException(String message) {
		super();
	}

}
