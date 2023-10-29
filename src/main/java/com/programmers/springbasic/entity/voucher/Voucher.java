package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

import com.programmers.springbasic.entity.customer.Customer;

public abstract class Voucher {
	protected final UUID voucherId;
	protected final VoucherType voucherType;
	protected Customer customer;

	protected Voucher(UUID voucherId, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public abstract long getDiscountValue();

	public abstract void changeDiscountValue(long newDiscountValue);

	public abstract void validateDiscountValue(long discountValue);
}
