package com.programmers.voucher.domain.customer.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Customer {

	private final UUID customerId;
	private final LocalDateTime createdAt;
	private CustomerType customerType;
	private LocalDateTime lastModifiedAt;

	public Customer(UUID customerId, LocalDateTime createdAt, CustomerType customerType, LocalDateTime modifiedAt) {
		this.customerId = customerId;
		this.createdAt = createdAt;
		this.customerType = customerType;
		this.lastModifiedAt = modifiedAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public LocalDateTime getLastModifiedAt() {
		return lastModifiedAt;
	}

	@Override
	public String toString() {
		return "customerId: " + customerId + ", customerType: " + customerType.name()
			+ ", createdAt: " + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			+ ", modifiedAt: " + lastModifiedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
