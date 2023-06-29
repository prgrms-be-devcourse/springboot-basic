package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class UnderMinimumAmountException extends RuntimeException {

	public UnderMinimumAmountException(ExceptionMessage message) {
		super(message.getMessage());
	}

}
