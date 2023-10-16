package com.programmers.springbasic.controller.dto;

import com.programmers.springbasic.entity.Customer;

public class ListBlacklistCustomerResponse {
	private long id;
	private String name;

	public ListBlacklistCustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
	}
}
