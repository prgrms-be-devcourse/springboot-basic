package com.voucher.vouchermanagement.domain.voucher.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DateTimePeriod {
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	@NotNull
	private LocalDateTime startAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	@NotNull
	private LocalDateTime endAt;

	protected DateTimePeriod() {

	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}
}