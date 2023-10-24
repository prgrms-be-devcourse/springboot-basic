package study.dev.spring.customer.infrastructure.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.customer.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerMapper {

	public static Customer toCustomer(final CustomerData customerData) {
		return new Customer(customerData.getUuid(), customerData.getName());
	}
}
