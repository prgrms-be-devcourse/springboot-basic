package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

	private final long amount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		super(voucherId, VoucherType.FIXED_AMOUNT);
		this.amount = amount;
	}

	@Override
	public long getDiscountValue() {
		return amount;
	}

}
