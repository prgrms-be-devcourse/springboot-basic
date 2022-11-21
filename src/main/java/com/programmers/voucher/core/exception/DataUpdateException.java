package com.programmers.voucher.core.exception;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

public class DataUpdateException extends RuntimeException {

	public DataUpdateException() {
		super(DATA_UPDATE_FAIL.getMessage());
	}
}
