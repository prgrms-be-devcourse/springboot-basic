package org.prgrms.springorder.exception;

import org.prgrms.springorder.domain.ErrorMessage;

public class FileLoadException extends RuntimeException {

	public FileLoadException(ErrorMessage message, Throwable cause) {
		super(message.toString(), cause);
	}
}
