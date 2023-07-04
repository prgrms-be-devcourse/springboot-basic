package com.example.voucher.utils.validator;

import com.example.voucher.constant.ConstantStrings;

public class VoucherValidator {

	private VoucherValidator() {

	}

	public static void validatePercent(long percent) {
		if (percent < 0 || percent > 100) {
			throw new IllegalArgumentException(ConstantStrings.MESSAGE_ERROR_RANGE_CONSTRAINT);
		}
	}

	public static void validateNonZero(long value) {
		if (value == 0) {
			throw new IllegalArgumentException(ConstantStrings.MESSAGE_ERROR_NON_ZERO_CONSTRAINT);
		}
	}

	public static void validatePositive(long value) {
		if (value <= 0) {
			throw new IllegalArgumentException(ConstantStrings.MESSAGE_ERROR_POSITIVE_CONSTRAINT);
		}
	}

	public static void validateGreaterThan(long value, long threshold) {
		if (value <= threshold) {
			throw new IllegalArgumentException(
				String.format("{} {}", ConstantStrings.FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
		}
	}

}
