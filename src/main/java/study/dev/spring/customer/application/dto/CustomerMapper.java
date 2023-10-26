package study.dev.spring.customer.application.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.customer.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerMapper {

	public static CustomerInfo toCustomerInfo(Customer customer) {
		return new CustomerInfo(customer.getUuid(), customer.getName());
	}
}
