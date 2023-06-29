package com.example.voucher.utils.validator;

public class VoucherValidator {

	public static void validatePercent(long percent) {
		if (percent < 0 || percent > 100) {
			throw new IllegalArgumentException("패센트 값은 0 과 100사이여야 합니다");
		}
	}

	public static void validateNonZero(long value) {
		if (value == 0) {
			throw new IllegalArgumentException("값은 0이 아니여야 합니다");
		}
	}

	public static void validatePositive(long value) {
		if (value <= 0) {
			throw new IllegalArgumentException("값은 양수여야 합니다");
		}
	}

	public static void validateGreaterThan(long value, long threshold) {
		if (value <= threshold) {
			throw new IllegalArgumentException("값은" + threshold + "보다 커야합니다.");
		}
	}
}
