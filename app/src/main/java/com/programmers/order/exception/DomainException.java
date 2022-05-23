package com.programmers.order.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DomainException {

	public static class ConstraintViolationException extends RuntimeException {
		public ConstraintViolationException(String message) {
			super(message);
		}
	}

	public static class DomainIntegrityException extends DataIntegrityViolationException {
		public DomainIntegrityException(String msg) {
			super(msg);
		}
	}
}
