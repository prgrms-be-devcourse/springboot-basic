package org.prgrms.kdt.common.response;

import org.prgrms.kdt.common.codes.ErrorCode;

public class ErrorResponse {
	private ErrorCode errorCode;

	public ErrorResponse(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
