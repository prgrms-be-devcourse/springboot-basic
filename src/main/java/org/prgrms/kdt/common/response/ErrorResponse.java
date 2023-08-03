package org.prgrms.kdt.common.response;

import org.prgrms.kdt.common.codes.ErrorCode;

public class ErrorResponse {
	private int errorNumber;

	public ErrorResponse(ErrorCode errorCode) {
		this.errorNumber = errorCode.getErrorNumber();
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}
}
