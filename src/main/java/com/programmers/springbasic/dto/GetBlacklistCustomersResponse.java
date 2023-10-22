package com.programmers.springbasic.dto;

import com.programmers.springbasic.entity.customer.Customer;

public class GetBlacklistCustomersResponse {

	private long id;
	private String name;

	public GetBlacklistCustomersResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
	}

	@Override
	public String toString() {
		return "고객 아이디 = " + id + ", 고객 이름 = " + name;
	}

}
