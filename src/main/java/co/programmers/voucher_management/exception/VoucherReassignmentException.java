package co.programmers.voucher_management.exception;

import lombok.Getter;

@Getter
public class VoucherReassignmentException extends RuntimeException {
	private final ErrorCode errorCode;

	public VoucherReassignmentException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
