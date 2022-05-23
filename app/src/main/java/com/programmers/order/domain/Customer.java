package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

/**
 * update 할 데이터가 없음.
 */
public class Customer {
	private UUID customerId;
	private String email;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public Customer(UUID customerId, String email, String name, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.customerId = customerId;
		this.email = email;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getEmail() {
		return email;
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
