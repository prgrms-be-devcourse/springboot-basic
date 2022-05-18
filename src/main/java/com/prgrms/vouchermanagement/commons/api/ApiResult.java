package com.prgrms.vouchermanagement.commons.api;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiResult<T> {
	private final HttpStatus statusCode;
	private final T data;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDateTime;

	private ApiResult(HttpStatus statusCode, T data) {
		this(statusCode, data, LocalDateTime.now());
	}

	private ApiResult(HttpStatus statusCode, T data, LocalDateTime serverDateTime) {
		this.statusCode = statusCode;
		this.data = data;
		this.serverDateTime = serverDateTime;
	}

	public static ApiResult<ApiError> error(HttpStatus statusCode, Throwable throwable) {
		return new ApiResult<>(statusCode, new ApiError(throwable));
	}

	public static <D> ApiResult<D> success(HttpStatus statusCode, D data) {
		return new ApiResult<>(statusCode, data);
	}

	public T getData() {
		return data;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public LocalDateTime getServerDateTime() {
		return serverDateTime;
	}
}
