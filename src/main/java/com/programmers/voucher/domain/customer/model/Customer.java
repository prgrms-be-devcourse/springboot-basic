package com.programmers.voucher.domain.customer.model;

import java.util.UUID;

public class Customer {

	private UUID customerId;
	private CustomerType customerType;

	public Customer(UUID customerId, CustomerType customerType) {
		this.customerId = customerId;
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return "ID: " + customerId + ", Type: " + customerType.name();
	}
}
