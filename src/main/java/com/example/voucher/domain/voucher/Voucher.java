package com.example.voucher.domain.voucher;

import java.util.Objects;

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

	public int getDiscountAmount() {
		return discountAmount;
	}

	public abstract int discount(int beforeDiscount);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Voucher voucher = (Voucher) o;
		return discountAmount == voucher.discountAmount && Objects.equals(voucherId, voucher.voucherId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherId, discountAmount);
	}
}
