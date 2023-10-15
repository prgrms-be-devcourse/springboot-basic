package com.programmers.springbasic.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

	private final UUID voucherId;
	private final long amount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		this.voucherId = voucherId;
		this.amount = amount;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.FIXED_AMOUNT;
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public long getPercent() {
		return 0;
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
			"voucherId=" + voucherId +
			", amount=" + amount +
			'}';
	}

}
