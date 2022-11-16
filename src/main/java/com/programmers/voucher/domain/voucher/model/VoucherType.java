package com.programmers.voucher.domain.voucher.model;

import java.util.Arrays;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public enum VoucherType {

	FIXED("FixedDiscountVoucher", discount -> discount < 0),
	PERCENT("PercentDiscountVoucher", discount -> discount <= 0 || discount > 100);

	private static final Logger log = LoggerFactory.getLogger(VoucherType.class);
	private final String name;
	private final Predicate<Double> OutOfDiscountRangeFilter;

	VoucherType(String name, Predicate<Double> OutOfDiscountRangeFilter) {
		this.name = name;
		this.OutOfDiscountRangeFilter = OutOfDiscountRangeFilter;
	}

	public String getName() {
		return name;
	}

	public boolean hasOutOfDiscountRange(double discount) {
		return OutOfDiscountRangeFilter.test(discount);
	}

	public static VoucherType getVoucherType(String voucherType) {
		return Arrays.stream(VoucherType.values())
			.filter(i -> i.name.equals(voucherType))
			.findFirst()
			.orElseThrow(() -> {
				log.error(ExceptionMessage.WRONG_VOUCHER_TYPE.getMessage());
				throw new IllegalArgumentException(ExceptionMessage.WRONG_VOUCHER_TYPE.getMessage());
			});
	}
}
