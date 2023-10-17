package com.programmers.springbasic.dto;

import java.text.MessageFormat;

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
		return MessageFormat.format("고객 아이디 = {0}, 고객 이름 = {1}", id, name);
	}
}
