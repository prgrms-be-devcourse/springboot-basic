package com.example.voucher.utils.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherValidator {

	private static final Logger logger = LoggerFactory.getLogger(VoucherValidator.class);

	private static final String RANGE_CONSTRAINT = "패센트 값은 0과 100 사이여야 합니다";
	private static final String NON_ZERO_CONSTRAINT = "값은 0이 아니여야 합니다";
	private static final String POSITIVE_CONSTRAINT = "값은 양수여야 합니다";
	private static final String GREATER_THAN_CONSTRAINT = "값은 threshold 보다 커야합니다.";

	private VoucherValidator() {

	}

	public static void validatePercent(long percent) {
		if (percent < 0 || percent > 100) {
			logger.error("error : {}", RANGE_CONSTRAINT);
			throw new IllegalArgumentException("패센트 값은 0 과 100사이여야 합니다");
		}
	}

	public static void validateNonZero(long value) {
		if (value == 0) {
			logger.error("error : {}", NON_ZERO_CONSTRAINT);
			throw new IllegalArgumentException("값은 0이 아니여야 합니다");
		}
	}

	public static void validatePositive(long value) {
		if (value <= 0) {
			logger.error("error : {}", POSITIVE_CONSTRAINT);
			throw new IllegalArgumentException("값은 양수여야 합니다");
		}
	}

	public static void validateGreaterThan(long value, long threshold) {
		if (value <= threshold) {
			logger.error("error : {} {}", GREATER_THAN_CONSTRAINT, threshold);
			throw new IllegalArgumentException("값은" + threshold + "보다 커야합니다.");
		}
	}
}
