package study.dev.spring.customer.domain;

import java.util.UUID;

public class Customer {

	private final UUID uuid;

	private String name;

	public Customer(
		final UUID uuid,
		final String name
	) {
		this.uuid = uuid;
		this.name = name;
	}

	//==Utility method==//
	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
}
