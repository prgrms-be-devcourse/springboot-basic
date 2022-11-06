package com.programmers.voucher.exception;

public class OutOfPercentRangeException extends RuntimeException {

	public OutOfPercentRangeException() {
		super(ExceptionMessage.OUT_OF_PERCENT_RANGE.getMessage());
	}
}
