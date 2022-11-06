package com.programmers.voucher.exception;

public class WrongDiscountTypeException extends RuntimeException {

	public WrongDiscountTypeException() {
		super(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
	}
}
