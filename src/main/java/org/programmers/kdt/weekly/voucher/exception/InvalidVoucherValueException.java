package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;

public class InvalidVoucherValueException extends IllegalArgumentException {

	private final ErrorCode errorCode;

	public InvalidVoucherValueException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
