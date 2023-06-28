package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class NoSuchMenuException extends NullPointerException {

	public NoSuchMenuException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
