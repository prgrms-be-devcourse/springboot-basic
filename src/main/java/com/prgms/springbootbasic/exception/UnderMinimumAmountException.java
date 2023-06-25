package com.prgms.springbootbasic.exception;

public class UnderMinimumAmountException extends RuntimeException {
	
	public UnderMinimumAmountException(String message) {
		super(message);
	}
	
}
