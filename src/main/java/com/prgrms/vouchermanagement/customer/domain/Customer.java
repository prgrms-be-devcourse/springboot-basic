package com.prgrms.vouchermanagement.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
	private final UUID customerId;
	private final String name;
	private final String email;
	private final LocalDateTime createdAt;
	private LocalDateTime lastLoginAt;

	public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
		validateName(name);
		this.name = name;
		this.customerId = customerId;
		this.email = email;
		this.createdAt = createdAt;
	}

	public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
		validateName(name);
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
	}

	private void validateName(String name) {
		if (name.isBlank()) {
			throw new IllegalArgumentException("name 은 공백이 올 수 없습니다");
		}
	}

	public void login() {
		this.lastLoginAt = LocalDateTime.now();
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
