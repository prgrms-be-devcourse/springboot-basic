package com.programmers.voucher.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {

	protected final UUID voucherId;
	protected final double discount;
	protected final VoucherType voucherType;
	protected final LocalDateTime createdAt;

	protected Voucher(UUID voucherId, String discount, VoucherType voucherType, LocalDateTime createdAt) {
		this.voucherId = voucherId;
		this.discount = validateDiscount(discount);
		this.voucherType = voucherType;
		this.createdAt = createdAt;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	protected abstract double validateDiscount(String discount);
}