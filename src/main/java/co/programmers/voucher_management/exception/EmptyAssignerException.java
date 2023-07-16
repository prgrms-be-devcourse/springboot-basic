package co.programmers.voucher_management.exception;

import lombok.Getter;

@Getter
public class EmptyAssignerException extends RuntimeException {
	private final ErrorCode errorCode;

	public EmptyAssignerException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
