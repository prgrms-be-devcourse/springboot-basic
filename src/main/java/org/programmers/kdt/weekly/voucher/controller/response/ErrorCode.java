package org.programmers.kdt.weekly.voucher.controller.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST),
	FAILED_INSERT(HttpStatus.NOT_FOUND),
	FAILED_UPDATE(HttpStatus.NOT_FOUND),
	NOT_FOUND_BY_VOUCHER_ID(HttpStatus.BAD_REQUEST);

	private final HttpStatus statusCode;

	ErrorCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
