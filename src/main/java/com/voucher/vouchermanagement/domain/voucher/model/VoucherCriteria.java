package com.voucher.vouchermanagement.domain.voucher.model;

import java.time.LocalDateTime;

import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

public class VoucherCriteria {

	VoucherType type;
	LocalDateTime startAt;
	LocalDateTime endAt;

	public VoucherCriteria() {
	}

	public VoucherCriteria(VoucherType type, LocalDateTime startAt, LocalDateTime endAt) {
		this.type = type;
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
}
