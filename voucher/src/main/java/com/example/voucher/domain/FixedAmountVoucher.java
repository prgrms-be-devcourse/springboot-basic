package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;

public class FixedAmountVoucher implements Voucher {

	private final UUID voucherId;
	VoucherType voucherType = VoucherType.PercentDiscount;
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
	public long discount(long beforeAmount) {
		return beforeAmount - amount;
	}

	@Override
	public String getInfo() {
		return String.format("VoucherType : %s, discountAmount : %d", voucherType.getTypeName(), amount);
	}
}
