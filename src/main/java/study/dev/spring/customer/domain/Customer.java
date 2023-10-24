package study.dev.spring.customer.domain;

public class Customer {

	private final String uuid;

	private String name;

	public Customer(
		final String uuid,
		final String name
	) {
		this.uuid = uuid;
		this.name = name;
	}

	//==Utility method==//
	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
}
