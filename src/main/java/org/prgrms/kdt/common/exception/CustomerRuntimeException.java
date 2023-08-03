package org.prgrms.kdt.common.exception;

import org.prgrms.kdt.common.codes.ErrorCode;

public class CustomerRuntimeException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomerRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
