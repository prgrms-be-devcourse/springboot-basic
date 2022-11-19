package com.programmers.voucher.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {

	protected final UUID voucherId;
	protected final double discount;
	protected final LocalDateTime createdAt;
	protected VoucherType voucherType;
	protected LocalDateTime lastModifiedAt;

	protected Voucher(UUID voucherId, String discount, LocalDateTime createdAt,
		VoucherType voucherType, LocalDateTime lastModifiedAt) {
		this.voucherId = voucherId;
		this.discount = validateDiscount(discount);
		this.createdAt = createdAt;
		this.voucherType = voucherType;
		this.lastModifiedAt = lastModifiedAt;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	protected abstract double validateDiscount(String discount);
}