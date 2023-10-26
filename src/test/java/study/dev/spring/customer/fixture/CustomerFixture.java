package study.dev.spring.customer.fixture;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.customer.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerFixture {

	public static Customer getCustomer() {
		return new Customer("id", "name");
	}

	public static List<Customer> getCustomers() {
		return List.of(new Customer("id1", "name1"), new Customer("id2", "name2"));
	}
}
