package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class DuplicationKeyException extends RuntimeException {

	public DuplicationKeyException(ExceptionMessage message) {
		super(message.getMessage());
	}

}
