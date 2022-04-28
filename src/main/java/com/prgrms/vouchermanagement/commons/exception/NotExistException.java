package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class NotExistException extends RuntimeException {
	public NotExistException() {
		super(ErrorMessage.NOT_EXIST.getInfoMessage());
	}
}
