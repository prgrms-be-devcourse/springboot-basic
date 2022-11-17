package org.prgrms.springorder.exception;

import org.prgrms.springorder.domain.ErrorMessage;

public class NoSuchCommandException extends RuntimeException {

	public NoSuchCommandException(ErrorMessage message) {
		super(message.toString());
	}
}
