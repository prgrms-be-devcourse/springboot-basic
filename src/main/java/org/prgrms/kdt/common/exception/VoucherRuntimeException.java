package org.prgrms.kdt.common.exception;

import org.prgrms.kdt.common.codes.ErrorCode;

public class VoucherRuntimeException extends RuntimeException {
	private final ErrorCode errorCode;

	public VoucherRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
