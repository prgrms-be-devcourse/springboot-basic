package com.programmers.voucher.exception;

public class WrongVoucherTypeException extends RuntimeException {

	public WrongVoucherTypeException() {
		super(ExceptionMessage.WRONG_VOUCHER_TYPE.getMessage());
	}
}
