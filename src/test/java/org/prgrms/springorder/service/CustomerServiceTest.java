package org.prgrms.springorder.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.repository.customer.CustomerRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

	private CustomerService customerService;

	private CustomerRepository customerRepository;

	@BeforeAll
	public void beforeAll() {
		customerRepository = mock(CustomerRepository.class);
		customerService = new CustomerService(customerRepository);
	}

	@Test
	@DisplayName("블랙리스트를 받아서 고객을 스트링으로 변환하여 성공적으로 반환한다.")
	void test1() {
		int size = 2;
		List<Customer> customerList = new ArrayList<>();
		Customer customer1 = new Customer(UUID.randomUUID(), "이건우", CustomerType.BLACK);
		Customer customer2 = new Customer(UUID.randomUUID(), "블랙독", CustomerType.BLACK);
		customerList.add(customer1);
		customerList.add(customer2);

		List<String> toStringBlackList = customerList.stream()
			.map(Customer::toString).toList();

		when(customerRepository.getBlackList()).thenReturn(customerList);

		//when
		List<String> blackList = customerService.getBlackList();

		//then
		Assertions.assertNotNull(blackList);
		Assertions.assertEquals(size, blackList.size());
		Assertions.assertLinesMatch(toStringBlackList, blackList);
		verify(customerRepository).getBlackList();
	}
}