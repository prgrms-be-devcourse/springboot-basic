package com.programmers.voucher.core.exception;

public class EmptyBufferException extends RuntimeException {

	public EmptyBufferException() {
		super(ExceptionMessage.EMPTY_BUFFER.getMessage());
	}
}
