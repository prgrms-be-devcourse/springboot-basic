package org.prgrms.kdt.common.exception;

import org.prgrms.kdt.common.codes.ErrorCode;

public class CommonRuntimeException extends RuntimeException{
	private final ErrorCode errorCode;

	public CommonRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
