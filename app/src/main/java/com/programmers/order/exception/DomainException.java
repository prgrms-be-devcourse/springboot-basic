package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class DomainException {

	private DomainException() {
	}

	public static class ConstraintException extends IllegalArgumentException {
		public ConstraintException(ErrorMessage message) {
			super(message.toString());
		}
	}

	public static class NotFoundResource extends RuntimeException {
		public NotFoundResource(ErrorMessage message) {
			super(message.toString());
		}
	}

	public static class NotEmptyField extends RuntimeException {
		public NotEmptyField(ErrorMessage message) {
			super(message.toString());
		}
	}
}
