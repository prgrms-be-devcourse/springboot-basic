package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class NoSuchVoucherTypeException extends NullPointerException {

	public NoSuchVoucherTypeException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
