package com.example.voucher.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
	FIXED_AMOUNT_VOUCHER,
	EMPTY;
	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
