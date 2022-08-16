package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class UpdateFailException extends RuntimeException {
	public UpdateFailException() {
		super(ErrorMessage.UPDATE_FAIL.getInfoMessage());
	}

	public UpdateFailException(Throwable cause) {
		super(ErrorMessage.UPDATE_FAIL.getInfoMessage(), cause);
	}
}
