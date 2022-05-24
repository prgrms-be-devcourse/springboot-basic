package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;

public class InvalidDateRequestException extends RuntimeException {
	private static final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;

	public InvalidDateRequestException(String message) {
		super(message);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
