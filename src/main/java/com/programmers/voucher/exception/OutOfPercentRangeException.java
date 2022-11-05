package com.programmers.voucher.exception;

public class OutOfPercentRangeException extends RuntimeException {

	public OutOfPercentRangeException() {
		super("잘못된 할인 범위입니다.");
	}
}
