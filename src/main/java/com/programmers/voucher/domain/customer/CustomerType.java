package com.programmers.voucher.domain.customer;

import java.util.Arrays;

public enum CustomerType {

	NORMAL, BLACKLIST;

	public static CustomerType getCustomerType(String customerType) {
		return Arrays.stream(CustomerType.values())
			.filter(t -> t.name().equals(customerType))
			.findFirst()
			.orElseThrow();
	}
}
