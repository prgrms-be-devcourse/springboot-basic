package org.programmers.kdt.weekly.voucher.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final String error;
	private final String code;
	private final String message;

	public ErrorResponse(ErrorCode errorCodeMessage, String message) {
		this.status = errorCodeMessage.getStatusCode().value();
		this.error = errorCodeMessage.getStatusCode().name();
		this.code = errorCodeMessage.name();
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
