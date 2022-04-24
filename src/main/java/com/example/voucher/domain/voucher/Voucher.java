package com.example.voucher.domain.voucher;

public abstract class Voucher {
	final Long voucherId;
	final int discountAmount;

	public Voucher(Long voucherId, int discountAmount) {
		this.voucherId = voucherId;
		this.discountAmount = discountAmount;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public abstract int discount(int beforeDiscount);
}
