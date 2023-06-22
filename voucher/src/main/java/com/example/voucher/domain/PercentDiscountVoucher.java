package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final long percent;
	VoucherType voucherType = VoucherType.PercentDiscount;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long discount(long beforeAmount) {
		return beforeAmount * (percent / 100);
	}

	@Override
	public String getInfo() {
		return String.format("VoucherType : %s, discountPercent : %d", voucherType.getTypeName(), percent);
	}
}
