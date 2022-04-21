package com.example.voucher;

import java.util.Arrays;

public enum VoucherType {

	EMPTY;
	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
