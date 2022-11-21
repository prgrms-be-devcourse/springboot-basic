package com.programmers.voucher.core.exception;

public class DataUpdateException extends RuntimeException {

	public DataUpdateException() {
		super(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
	}
}
