package com.programmers.voucher.exception;

public class WrongDiscountTypeException extends RuntimeException {

	public WrongDiscountTypeException() {
		super("할인값을 숫자로 입력해주세요");
	}
}
