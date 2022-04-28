package com.example.voucher.domain.voucher;

import java.util.Objects;

public abstract class Voucher {
	final VoucherType voucherType;
	final Long voucherId;
	final int discountAmount;

	public Voucher(VoucherType voucherType, Long voucherId, int discountAmount) {
		this.voucherType = voucherType;
		this.voucherId = voucherId;
		this.discountAmount = discountAmount;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public abstract int discount(int beforeDiscount);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Voucher voucher = (Voucher) o;
		return discountAmount == voucher.discountAmount && voucherType == voucher.voucherType && Objects.equals(voucherId, voucher.voucherId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherType, voucherId, discountAmount);
	}
}
