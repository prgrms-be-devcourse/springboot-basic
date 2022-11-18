package org.prgrms.springorder.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

	private final UUID customerId;
	private final String name;
	private final String email;
	private final LocalDateTime createdAt;
	private CustomerType customerType;

	public Customer(UUID customerId, String name, String email, LocalDateTime createdAt,
		CustomerType customerType) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return String.format("ID : %s Name : %s", customerId, name);
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}
}
