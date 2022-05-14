package com.programmers.order.domain;

import com.programmers.order.exception.DomainException;

public enum VoucherType {
	FIX(1, 100_000_000),
	PERCENT(1, 100);

	private final int min;
	private final int max;

	VoucherType(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public void checkConstraint(long discountValue) {
		if (this.max < discountValue || this.min > discountValue) {
			throw new DomainException.ConstraintViolationException(this.name() + " voucher 객체 생성을 실패했습니다..");
		}
	}
}
