package com.programmers.voucher.exception;

public class WrongCommandException extends RuntimeException {

	public WrongCommandException() {
		super(ExceptionMessage.WRONG_COMMAND.getMessage());
	}
}
