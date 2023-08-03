package org.prgrms.kdt.model.entity;

import java.time.LocalDateTime;

public class CustomerEntity {

	private final Long customerId;
	private final String name;
	private final String email;
	private final LocalDateTime createdAt;

	public CustomerEntity(Long customerId, String name, String email, LocalDateTime createdAt) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
