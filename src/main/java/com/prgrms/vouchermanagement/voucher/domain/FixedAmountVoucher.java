package com.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	private final UUID voucherId;
	private final long amount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("FixedAmountVoucher - Amount should be positive");
		}
		this.voucherId = voucherId;
		this.amount = amount;
	}

	@Override
	public UUID getVoucherId() {
		return this.voucherId;
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
