package com.programmers.voucher.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
