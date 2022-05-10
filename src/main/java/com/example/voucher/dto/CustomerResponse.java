package com.example.voucher.dto;

import com.example.voucher.domain.customer.Customer;

public class CustomerResponse {
	private final Long customerId;
	private final String name;

	public CustomerResponse(Long customerId, String name) {
		this.customerId = customerId;
		this.name = name;
	}

	public static CustomerResponse from(Customer customer) {
		return new CustomerResponse(customer.getCustomerId(), customer.getName());
	}
}
