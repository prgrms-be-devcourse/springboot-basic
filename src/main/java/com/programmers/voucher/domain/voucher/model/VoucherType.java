package com.programmers.voucher.domain.voucher.model;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {

	FIXED("fixed"),
	PERCENT("percent");

	private static final Logger log = LoggerFactory.getLogger(VoucherType.class);
	private final String type;

	VoucherType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static VoucherType getVoucherType(String voucherType) {
		return Arrays.stream(VoucherType.values())
			.filter(voucher -> voucher.type.equals(voucherType.toLowerCase()))
			.findFirst()
			.orElseThrow(() -> {
				log.error(WRONG_VOUCHER_TYPE.getMessage());
				throw new IllegalArgumentException(WRONG_VOUCHER_TYPE.getMessage());
			});
	}
}
