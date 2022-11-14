package org.prgrms.springorder.exception;

import org.prgrms.springorder.domain.ErrorMessage;

public class FileSaveException extends RuntimeException {

	public FileSaveException(ErrorMessage message, Throwable cause) {
		super(message.toString(), cause);
	}
}
