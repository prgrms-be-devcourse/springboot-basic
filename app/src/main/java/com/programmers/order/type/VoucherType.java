package com.programmers.order.type;

import java.util.Arrays;

public enum VoucherType {
	FIX_VOUCHER(1), PERCENT_VOUCHER(2), NONE(0);

	private final int key;

	VoucherType(int key) {
		this.key = key;
	}

	public static VoucherType getVoucherType(String input) {
		return Arrays.stream(VoucherType.values())
				.filter(voucherType -> voucherType.key == Integer.parseInt(input))
				.findAny()
				.orElse(NONE);
	}

}
