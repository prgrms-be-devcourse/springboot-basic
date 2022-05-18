package com.prgrms.vouchermanagement.commons.api;

public class ApiError {
	private final String errorMessage;

	public ApiError(Throwable throwable) {
		this.errorMessage = throwable.getMessage();
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
