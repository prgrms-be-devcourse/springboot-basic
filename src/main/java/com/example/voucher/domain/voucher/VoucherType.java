package com.example.voucher.domain.voucher;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum VoucherType {
	FIXED_AMOUNT_VOUCHER((voucherId, discountAmount) -> new FixedAmountVoucher(voucherId, discountAmount)),
	PERCENT_DISCOUNT_VOUCHER((voucherId, discountAmount) -> new PercentDiscountVoucher(voucherId, discountAmount)),
	EMPTY(null);

	private BiFunction<Long, Integer, Voucher> expression;

	VoucherType(BiFunction<Long, Integer, Voucher> expression) {
		this.expression = expression;
	}

	public Voucher create(Long voucherId, int discountAmount) {
		return expression.apply(voucherId, discountAmount);
	}

	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
