package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class NoSuchVoucherTypeException extends NullPointerException {

	public NoSuchVoucherTypeException(ExceptionMessage message) {
		super(message.getMessage());
	}
	
}
