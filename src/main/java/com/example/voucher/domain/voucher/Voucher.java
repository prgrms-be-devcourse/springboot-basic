package com.example.voucher.domain.voucher;

public abstract class Voucher {
	int discountAmount;

	public abstract int discount(int beforeDiscount);
}
