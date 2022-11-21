package com.programmers.voucher.core.exception;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

public class EmptyBufferException extends RuntimeException {

	public EmptyBufferException() {
		super(EMPTY_BUFFER.getMessage());
	}
}
