package com.programmers.voucher.domain.voucher;

public enum VoucherType {

	FIXED("FixedDiscountVoucher"),
	PERCENT("PercentDiscountVoucher");

	private String type;

	VoucherType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
