package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class OutOfRangePercentException extends RuntimeException {

	public OutOfRangePercentException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
