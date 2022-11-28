package org.prgrms.springorder.domain.customer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {

	private final UUID customerId;
	private final String name;
	private final String email;
	private final LocalDateTime customerCreatedAt;
	private CustomerType customerType;

	public Customer(UUID customerId, String name, String email, LocalDateTime customerCreatedAt,
		CustomerType customerType) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.customerCreatedAt = customerCreatedAt;
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

	public LocalDateTime getCustomerCreatedAt() {
		return customerCreatedAt;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Customer customer = (Customer)o;
		return Objects.equals(getCustomerId(), customer.getCustomerId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerId());
	}
}
