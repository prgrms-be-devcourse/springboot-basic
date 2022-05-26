package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
		super(VoucherType.FIXED, voucherId, createdAt, new FixedDiscounter(), amount);

		if (amount <= 0) {
			throw new IllegalArgumentException("FixedAmountVoucher - Amount should be positive");
		}
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
			"amount=" + this.getDiscountInfo() +
			'}';
	}

}
