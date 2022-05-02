package com.example.voucher.dto;

import com.example.voucher.domain.voucher.VoucherType;

import java.time.LocalDateTime;

public class VoucherSearchRequest {
	private final VoucherType voucherType;
	private final LocalDateTime createdAt;

	public VoucherSearchRequest(VoucherType voucherType, LocalDateTime createdAt) {
		this.voucherType = voucherType;
		this.createdAt = createdAt;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
