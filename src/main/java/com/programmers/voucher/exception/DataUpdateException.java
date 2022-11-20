package com.programmers.voucher.exception;

public class DataUpdateException extends RuntimeException {

	public DataUpdateException() {
		super(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
	}
}
