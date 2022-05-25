package com.programmers.order.exception;

import org.springframework.boot.info.JavaInfo;

import com.programmers.order.message.ErrorMessage;

public class ServiceException {
	public static class NotSupportedException extends RuntimeException {
		public NotSupportedException(ErrorMessage message) {
			super(message.toString());
		}
	}

	public static class InternalBugException extends RuntimeException {
		public InternalBugException(ErrorMessage message) {
			super(message.toString());
		}
	}

}
