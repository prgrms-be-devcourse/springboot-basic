package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class FixedAmountVoucher extends Voucher {
	private final long amount;

	public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
		super(VoucherType.FIXED, voucherId, createdAt);
		if (amount <= 0) {
			throw new IllegalArgumentException("FixedAmountVoucher - Amount should be positive");
		}
		this.amount = amount;
	}

	@Override
	public long getDiscountInfo() {
		return amount;
	}

	@Override
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
