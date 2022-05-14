package com.programmers.order.exception;

public class ServiceException {

	public static class NotSupportedException extends RuntimeException {
		public NotSupportedException(String message) {
			super(message);
		}
	}

	public static class NotFoundResource extends RuntimeException {
		public NotFoundResource(String message) {
			super(message);
		}
	}
}
