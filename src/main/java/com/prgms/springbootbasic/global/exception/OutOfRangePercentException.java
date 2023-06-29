package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class OutOfRangePercentException extends RuntimeException {

	public OutOfRangePercentException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
