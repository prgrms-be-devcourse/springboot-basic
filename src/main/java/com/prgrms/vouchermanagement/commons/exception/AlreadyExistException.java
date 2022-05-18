package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class AlreadyExistException extends RuntimeException{
	public AlreadyExistException() {
		super(ErrorMessage.ALREADY_EXIST.getInfoMessage());
	}
}
