package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedDiscountVoucher implements Voucher {

	private final UUID voucherId;
	private final int discountAmount;

	public FixedDiscountVoucher(UUID voucherId, int discountAmount) {
		this.voucherId = voucherId;
		this.discountAmount = discountAmount;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public int discount(int beforeDiscount) {
		return beforeDiscount - discountAmount;
	}

	@Override
	public String toString() {
		return "ID: " + voucherId + ", Type: FixedDiscountVoucher, Discount: " + discountAmount;
	}
}
