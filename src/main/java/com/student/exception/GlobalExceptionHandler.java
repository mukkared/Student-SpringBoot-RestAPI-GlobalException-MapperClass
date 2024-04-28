package com.student.exception;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	public String todayDate() {
		LocalDate today = LocalDate.now();
		// Define a date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Format today's date using the defined format
		return today.format(formatter);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<?> HandleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<?> handleStudentAlreadyExistsException(StudentAlreadyExistsException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

//	@ExceptionHandler({Exception.class,StudentAlreadyExistsException.class,StudentNotFoundException.class})
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}