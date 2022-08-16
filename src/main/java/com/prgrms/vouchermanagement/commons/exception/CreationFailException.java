package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class CreationFailException extends RuntimeException {
	public CreationFailException(Throwable cause) {
		super(ErrorMessage.CREATION_FAIL
			.getInfoMessage(), cause);
	}
}
