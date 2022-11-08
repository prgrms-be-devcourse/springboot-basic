package com.programmers.voucher.domain.customer.model;

import java.util.Arrays;

public enum CustomerType {

	NORMAL, BLACKLIST;

	public static CustomerType getCustomerType(String customerType) {
		return Arrays.stream(CustomerType.values())
			.filter(type -> type.name().equals(customerType))
			.findFirst()
			.orElseThrow();
	}
}
