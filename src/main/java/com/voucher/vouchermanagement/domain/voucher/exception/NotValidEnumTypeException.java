package com.voucher.vouchermanagement.domain.voucher.exception;

public class NotValidEnumTypeException extends IllegalArgumentException{
	public NotValidEnumTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
