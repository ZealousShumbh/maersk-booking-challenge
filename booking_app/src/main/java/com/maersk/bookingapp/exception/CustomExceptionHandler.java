package com.maersk.bookingapp.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maersk.bookingapp.model.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'ResourceNotFoundException' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		int status = httpStatus.value();
		String error = "Resource not found.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException ex, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'NoDataFoundException' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		int status = httpStatus.value();
		String error = "No data is found.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'IllegalArgumentException' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		HttpStatus httpStatus = HttpStatus.CONFLICT;
		int status = httpStatus.value();
		String error = "Illegal Argument Given.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus httpStatus, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'MethodArgumentNotValidException' exception");

		List<String> details = new ArrayList<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			details.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
		}
		for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
			details.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
		}

		int status = httpStatus.value();
		String error = "Constraint Validation Failed.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'MethodArgumentTypeMismatchException' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getName() + " should be of type " + ex.getRequiredType().getName());
		details.add(ex.getLocalizedMessage());

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		int status = httpStatus.value();
		String error = "Method argument is not the expected type.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleMConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'ConstraintViolationException' exception");

		List<String> details = new ArrayList<>();
		
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			details.add(violation.getRootBeanClass().getName() + " " + 
	          violation.getPropertyPath() + ": " + violation.getMessage());
	    }

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		int status = httpStatus.value();
		String error = "Constraint Validation has Failed.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus httpStatus, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'HttpMessageNotReadableException' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		int status = httpStatus.value();
		String error = "Malformed JSON request.";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		logger.info("Using CustomExceptionHandler to handle caught 'Exception' exception");

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		int status = httpStatus.value();
		String error = "Internal Server Error";
		String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, error, details, path);

		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}
}
