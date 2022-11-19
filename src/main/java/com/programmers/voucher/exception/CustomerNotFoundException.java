package com.programmers.voucher.exception;

public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException() {
		super(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
	}
}
