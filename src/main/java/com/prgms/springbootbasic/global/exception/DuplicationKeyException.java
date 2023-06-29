package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class DuplicationKeyException extends RuntimeException {

	public DuplicationKeyException(ExceptionMessage message) {
		super(message.getMessage());
	}

}
