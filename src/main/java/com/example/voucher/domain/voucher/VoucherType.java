package com.example.voucher.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
	FIXED_AMOUNT_VOUCHER("FixedAmountVoucher"),
	PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher"),
	EMPTY("null");

	private String typeString;

	VoucherType(String typeString) {
		this.typeString = typeString;
	}

	public String getTypeString() {
		return typeString;
	}

	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.typeString.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
