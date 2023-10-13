package study.dev.spring.voucher.exception;

import study.dev.spring.common.exception.CommonException;

public class VoucherException extends CommonException {

	private final VoucherErrorCode errorCode;

	public VoucherException(final VoucherErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
