package com.programmers.voucher.exception;

public class EmptyBufferException extends RuntimeException {

	public EmptyBufferException() {
		super(ExceptionMessage.EMPTY_BUFFER.getMessage());
	}
}
