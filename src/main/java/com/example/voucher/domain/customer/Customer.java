package com.example.voucher.domain.customer;

import java.time.LocalDateTime;

public class Customer {
	private final Long customerId;
	private final String name;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public Customer(String name) {
		this.customerId = null;
		this.name = name;
		createdAt = LocalDateTime.now();
		updatedAt = null;
	}

	public Customer(Long customerId, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.customerId = customerId;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
