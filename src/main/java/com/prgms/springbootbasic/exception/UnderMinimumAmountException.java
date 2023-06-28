package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class UnderMinimumAmountException extends RuntimeException {

	public UnderMinimumAmountException(ExceptionMessage message) {
		super(message.getMessage());
	}

}
