package org.programmers.kdt.weekly.voucher.controller.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiResponse<T> {

	private final T data;
	private final HttpStatus httpStatus = HttpStatus.OK;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDatetime;

	public ApiResponse(T data) {
		this.data = data;
		this.serverDatetime = LocalDateTime.now();
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(data);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public T getData() {
		return data;
	}

	public LocalDateTime getServerDatetime() {
		return serverDatetime;
	}
}
