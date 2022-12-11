package com.programmers.voucher.domain.customer.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public enum CustomerType {

	NORMAL, BLACKLIST;

	private static final Logger log = LoggerFactory.getLogger(CustomerType.class);

	public static CustomerType getCustomerType(String customerType) {
		return Arrays.stream(CustomerType.values())
			.filter(type -> type.name().equals(customerType))
			.findFirst()
			.orElseThrow(() -> {
				log.error(ExceptionMessage.WRONG_CUSTOMER_TYPE.getMessage());
				throw new IllegalArgumentException(ExceptionMessage.WRONG_CUSTOMER_TYPE.getMessage());
			});
	}
}
