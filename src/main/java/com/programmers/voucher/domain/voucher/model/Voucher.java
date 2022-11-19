package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

public abstract class Voucher {

	protected final UUID voucherId;
	protected final VoucherType voucherType;
	protected final double discount;

	protected Voucher(UUID voucherId, VoucherType voucherType, String discount) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
		this.discount = validateDiscount(discount);
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public double getDiscount() {
		return discount;
	}

	protected abstract double validateDiscount(String discount);
}