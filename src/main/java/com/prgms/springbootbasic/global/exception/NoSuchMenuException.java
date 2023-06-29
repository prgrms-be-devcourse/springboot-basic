package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class NoSuchMenuException extends NullPointerException {

	public NoSuchMenuException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
