package com.programmers.order.domain.constraint;

public enum VoucherConstraint {
	FIX_VOUCHER(1, 100_000_000), PERCENT_VOUCHER(1, 100);

	private final int min;
	private final int max;

	VoucherConstraint(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public boolean isViolate(long value) {
		return value < min || value > max;
	}

}
