package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

public abstract class Voucher {
	protected final UUID voucherId;
	protected final VoucherType voucherType;

	public Voucher(UUID voucherId, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public abstract long getDiscountValue();
}
