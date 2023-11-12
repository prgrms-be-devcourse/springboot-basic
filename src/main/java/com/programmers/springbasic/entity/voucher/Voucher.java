package com.programmers.springbasic.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
	protected final UUID voucherId;
	protected final VoucherType voucherType;
	protected final LocalDateTime createdAt;

	protected Voucher(UUID voucherId, VoucherType voucherType, LocalDateTime createdAt) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
		this.createdAt = createdAt;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public abstract long getDiscountValue();

	public abstract void changeDiscountValue(long newDiscountValue);

	public abstract void validateDiscountValue(long discountValue);
}
