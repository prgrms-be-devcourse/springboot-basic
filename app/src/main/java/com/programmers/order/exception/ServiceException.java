package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class ServiceException {
	public static class NotSupportedException extends RuntimeException {
		public NotSupportedException(ErrorMessage message) {
			super(message.toString());
		}
	}
}
