package com.programmers.springbasic.entity.voucher;

import java.util.Arrays;

import com.programmers.springbasic.constants.ErrorCode;

public enum VoucherType {
	FIXED_AMOUNT("fixed"), PERCENT_DISCOUNT("percent");

	private final String message;

	VoucherType(String message) {
		this.message = message;
	}

	public static VoucherType from(String readString) {
		return Arrays.stream(VoucherType.values())
			.filter(type -> type.message.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_VOUCHER_TYPE.getMessage()));
	}
}
