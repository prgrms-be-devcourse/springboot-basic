package com.example.voucher.domain.voucher;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
	FIXED_AMOUNT_VOUCHER(discountAmount -> new FixedAmountVoucher(discountAmount)),
	PERCENT_DISCOUNT_VOUCHER(discountAmount -> new PercentDiscountVoucher(discountAmount)),
	EMPTY(null);

	private Function<Integer, Voucher> expression;

	VoucherType(Function<Integer, Voucher> expression) {
		this.expression = expression;
	}

	public Voucher create(int discountAmount) {
		return expression.apply(discountAmount);
	}

	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
