package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class FixedAmountVoucher implements Voucher {
	private final VoucherType type = VoucherType.FIXED;
	private final UUID voucherId;
	private final long amount;
	private final LocalDateTime createdAt;

	public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
		if (amount <= 0) {
			throw new IllegalArgumentException("FixedAmountVoucher - Amount should be positive");
		}
		this.voucherId = voucherId;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	@Override
	public long getDiscountInfo() {
		return amount;
	}

	@Override
	public VoucherType getType() {
		return type;
	}

	@Override
	public UUID getVoucherId() {
		return this.voucherId;
	}

	@Override
	public LocalDateTime getCreatedTime() {
		return createdAt;
	}

	public long discount(long beforeDiscount) {
		long discountedAmount = beforeDiscount - amount;

		return (discountedAmount < 0) ? 0 : discountedAmount;
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
			"amount=" + amount +
			'}';
	}

}
