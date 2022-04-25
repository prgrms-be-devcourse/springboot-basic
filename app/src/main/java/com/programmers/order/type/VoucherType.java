package com.programmers.order.type;

import java.util.Arrays;

import com.programmers.order.utils.PatternUtils;

public enum VoucherType {
	NONE(0), FIX_VOUCHER(1), PERCENT_VOUCHER(2);

	private final int key;

	VoucherType(int key) {
		this.key = key;
	}

	public static VoucherType of(String input) {
		if (!PatternUtils.isNumeric(input)) {
			return NONE;
		}

		return Arrays.stream(VoucherType.values())
				.filter(voucherType -> voucherType.key == Integer.parseInt(input))
				.findAny()
				.orElse(NONE);
	}

	public static VoucherType MatchTheType(String input) {
		return Arrays.stream(VoucherType.values())
				.filter(voucherType -> voucherType.name().equalsIgnoreCase(input))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException());
	}

	public boolean isReEnter() {
		return this == NONE;
	}
}
