package com.programmers.voucher.web.voucher.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.voucher.domain.voucher.model.VoucherType;

public class VoucherResponseDto {

	private UUID voucherId;
	private double discount;
	private VoucherType voucherType;
	private LocalDateTime createdAt;

	public VoucherResponseDto(
		UUID voucherId,
		double discount,
		VoucherType voucherType,
		LocalDateTime createdAt
	) {
		this.voucherId = voucherId;
		this.discount = discount;
		this.voucherType = voucherType;
		this.createdAt = createdAt;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public String getDiscount() {
		if (voucherType.equals(VoucherType.FIXED)) {
			return Math.round(discount) + "원";
		}
		return discount + "%";
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
