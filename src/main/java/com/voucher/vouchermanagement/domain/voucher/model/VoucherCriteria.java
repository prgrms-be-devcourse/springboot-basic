package com.voucher.vouchermanagement.domain.voucher.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class VoucherCriteria {

	private VoucherType type;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime startAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime endAt;

	public VoucherCriteria() {
	}

	public VoucherCriteria(String type, LocalDateTime startAt, LocalDateTime endAt) {
		if (type != null)
			this.type = VoucherType.fromName(type);
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public VoucherType getType() {
		return type;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setType(String type) {
		if (type != null)
			this.type = VoucherType.fromName(type);
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}
}
