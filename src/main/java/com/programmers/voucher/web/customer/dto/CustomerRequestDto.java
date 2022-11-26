package com.programmers.voucher.web.customer.dto;

import com.programmers.voucher.domain.customer.model.CustomerType;

public class CustomerRequestDto {

	private CustomerType customerType;

	public CustomerRequestDto() {
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
