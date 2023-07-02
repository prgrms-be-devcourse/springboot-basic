package com.example.voucher.utils.validator;

public class VoucherValidator {

	private static final String RANGE_CONSTRAINT = "패센트 값은 0과 100 사이여야 합니다";
	private static final String NON_ZERO_CONSTRAINT = "값은 0이 아니여야 합니다";
	private static final String POSITIVE_CONSTRAINT = "값은 양수여야 합니다";
	private static final String GREATER_THAN_CONSTRAINT = "값은 threshold 보다 커야합니다.";

	private VoucherValidator() {

	}

	public static void validatePercent(long percent) {
		if (percent < 0 || percent > 100) {
			throw new IllegalArgumentException(RANGE_CONSTRAINT);
		}
	}

	public static void validateNonZero(long value) {
		if (value == 0) {
			throw new IllegalArgumentException(NON_ZERO_CONSTRAINT);
		}
	}

	public static void validatePositive(long value) {
		if (value <= 0) {
			throw new IllegalArgumentException(POSITIVE_CONSTRAINT);
		}
	}

	public static void validateGreaterThan(long value, long threshold) {
		if (value <= threshold) {
			throw new IllegalArgumentException(String.format("{} {}", GREATER_THAN_CONSTRAINT, threshold));
		}
	}

}
