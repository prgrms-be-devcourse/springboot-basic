package com.programmers.voucher.domain.customer.model;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CustomerType {

	NORMAL, BLACKLIST;

	private static final Logger log = LoggerFactory.getLogger(CustomerType.class);

	public static CustomerType getCustomerType(String customerType) {
		return Arrays.stream(CustomerType.values())
			.filter(type -> type.name().equals(customerType.toUpperCase()))
			.findFirst()
			.orElseThrow(() -> {
				log.error(WRONG_CUSTOMER_TYPE.getMessage());
				throw new IllegalArgumentException(WRONG_CUSTOMER_TYPE.getMessage());
			});
	}
}
