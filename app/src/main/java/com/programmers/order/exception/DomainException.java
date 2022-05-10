package com.programmers.order.exception;

public class DomainException {

	public static class ConstraintViolationException extends RuntimeException {
		public ConstraintViolationException(String message) {
			super(message);
		}
	}
}
