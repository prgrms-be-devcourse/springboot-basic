package com.programmers.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

	private final UUID voucherId;
	private final int percent;

	public PercentDiscountVoucher(UUID voucherId, int percent) {
		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public int discount(int beforeDiscount) {
		return beforeDiscount * (percent / 100);
	}

	@Override
	public String toString() {
		return "Type: PercentDiscountVoucher, discount: " + percent + "%";
	}
}
