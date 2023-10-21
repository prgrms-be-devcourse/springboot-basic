package com.programmers.springbasic.entity.customer;

public class Customer {
	private Long id;
	private String name;
	private Boolean isBlacklisted;

	public Customer(Long id, String name, Boolean isBlacklisted) {
		this.id = id;
		this.name = name;
		this.isBlacklisted = isBlacklisted;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean isBlacklisted() {
		return isBlacklisted;
	}
}
