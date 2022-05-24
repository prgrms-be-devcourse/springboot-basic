package org.programmers.kdt.weekly.voucher.controller.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST),
	NOT_FOUND_VOUCHER_BY_ID(HttpStatus.BAD_REQUEST),
	FAILED_EXECUTE_QUERY(HttpStatus.INTERNAL_SERVER_ERROR);

	private final HttpStatus statusCode;

	ErrorCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
