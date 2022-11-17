package org.prgrms.springorder.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

	private final UUID customerId;
	private final String name;
	private final String email;
	private final LocalDateTime createdAt;
	private final CustomerType customerType;

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


}
