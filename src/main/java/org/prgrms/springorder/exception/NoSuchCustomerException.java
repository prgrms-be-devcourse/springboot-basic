package org.prgrms.springorder.exception;

import org.prgrms.springorder.domain.ErrorMessage;

public class NoSuchCustomerException extends RuntimeException{
	public NoSuchCustomerException(ErrorMessage message) {
		super(message.toString());
	}
}
