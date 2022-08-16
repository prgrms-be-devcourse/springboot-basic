package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class DeletionFailException extends RuntimeException{
	public DeletionFailException() {
		super(ErrorMessage.DELETION_FAIL.getInfoMessage());
	}
}
