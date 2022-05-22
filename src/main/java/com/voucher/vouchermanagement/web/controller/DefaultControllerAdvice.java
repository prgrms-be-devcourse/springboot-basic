package com.voucher.vouchermanagement.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.voucher.vouchermanagement.exception.DataNotFoundException;

@ControllerAdvice
public class DefaultControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
		log.debug(exception.getMessage(), exception);

		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException exception) {
		log.debug(exception.getMessage(), exception);

		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<String> handleDataAccessException(DataAccessException exception) {
		log.debug(exception.getMessage(), exception);

		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
		log.warn(exception.getMessage(), exception);

		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {
		log.error("unexpected Exception {}", exception.getMessage(), exception);

		return ResponseEntity.internalServerError().build();
	}
}
