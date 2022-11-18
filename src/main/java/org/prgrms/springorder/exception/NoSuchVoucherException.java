package org.prgrms.springorder.exception;

import org.prgrms.springorder.domain.ErrorMessage;

public class NoSuchVoucherException extends RuntimeException{

	public NoSuchVoucherException(ErrorMessage message) {
		super(message.toString());
	}

}
