package com.programmers.springbasic.dto;

import com.programmers.springbasic.entity.customer.Customer;

public class ListBlacklistCustomerResponse {
	private long id;
	private String name;

	public ListBlacklistCustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
	}

	@Override
	public String toString() {
		return "ListBlacklistCustomerResponse{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
