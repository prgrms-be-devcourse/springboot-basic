package com.programmers.voucher.domain.voucher.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public enum VoucherType {

	FIXED("FixedDiscountVoucher"),
	PERCENT("PercentDiscountVoucher");

	private static final Logger log = LoggerFactory.getLogger(VoucherType.class);
	private String name;

	VoucherType(String name) {
		this.name = name;
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

	public String getName() {
		return name;
	}
}
