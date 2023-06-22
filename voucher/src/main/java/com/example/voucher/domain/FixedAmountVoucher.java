package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;

public class FixedAmountVoucher implements Voucher {

	private final UUID voucherId;
	private final long amount;
	private final VoucherType voucherType = VoucherType.FixedAmount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		this.voucherId = voucherId;
		this.amount = amount;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long discount(long beforeAmount) {
		return beforeAmount - amount;
	}

	@Override
	public void printInfo() {
		System.out.println(String.format("VoucherType : %s, discountAmount : %d", voucherType.getTypeName(), amount));
	}
}
