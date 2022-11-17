package org.prgrms.springorder.domain.customer;

import java.util.UUID;

public class Customer {

	private final UUID uuid;
	private final String name;
	private final CustomerType customerType;

	public Customer(UUID uuid, String name, CustomerType customerType) {
		this.uuid = uuid;
		this.name = name;
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return String.format("ID : %s Name : %s", uuid, name);
	}
}
