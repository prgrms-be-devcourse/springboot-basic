package com.example.voucher.domain.voucher;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum VoucherType {
	FIXED_AMOUNT_VOUCHER("FixedAmountVoucher", (voucherId, discountAmount) -> new FixedAmountVoucher(voucherId, discountAmount)),
	PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher", (voucherId, discountAmount) -> new PercentDiscountVoucher(voucherId, discountAmount)),
	EMPTY("null", null);

	private String typeString;
	private BiFunction<Long, Integer, Voucher> expression;

	VoucherType(String typeString, BiFunction<Long, Integer, Voucher> expression) {
		this.typeString = typeString;
		this.expression = expression;
	}

	public Voucher create(Long voucherId, int discountAmount) {
		return expression.apply(voucherId, discountAmount);
	}

	public static VoucherType of(String voucherTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.typeString.equals(voucherTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}
