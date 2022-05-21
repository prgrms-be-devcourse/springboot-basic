package com.voucher.vouchermanagement.exception;

public class NotValidEnumTypeException extends IllegalArgumentException {
	public NotValidEnumTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidEnumTypeException(String message) {
		super(message);
	}
}
