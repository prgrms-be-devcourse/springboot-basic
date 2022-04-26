package com.example.voucher.domain.voucher;

public abstract class Voucher {
	Long voucherId;
	int discountAmount;

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public abstract int discount(int beforeDiscount);
}
