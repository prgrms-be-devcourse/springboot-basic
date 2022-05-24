package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;

public class FailedToExecuteSqlException extends RuntimeException {
	private static final ErrorCode errorCode = ErrorCode.FAILED_EXECUTE_QUERY;

	public FailedToExecuteSqlException(String message) {
		super(message);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
