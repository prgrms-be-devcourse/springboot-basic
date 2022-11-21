package com.programmers.voucher.core.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
