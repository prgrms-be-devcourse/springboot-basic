package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class FileException {
	public static class NotCreateFileException extends RuntimeException {
		public NotCreateFileException(ErrorMessage message) {
			super(message.toString());
		}
	}
}
