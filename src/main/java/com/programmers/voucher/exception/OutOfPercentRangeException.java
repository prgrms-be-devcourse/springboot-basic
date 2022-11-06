package com.programmers.voucher.exception;

public class OutOfPercentRangeException extends RuntimeException {

	public OutOfPercentRangeException() {
		super("할인 비율은 0보다 크고 100이하여야 합니다.");
	}
}
