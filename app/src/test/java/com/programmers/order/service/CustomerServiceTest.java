package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.order.domain.Customer;
import com.programmers.order.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@DisplayName("회원 생성")
	@Test
	void testCreate() {

		// given
		Customer requestCustomer = Customer.builder()
				.customerId(UUID.randomUUID())
				.email("test@programmers.co.kr")
				.name("test")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		BDDMockito.given(customerRepository.insert(requestCustomer)).willReturn(requestCustomer);

		// when
		Customer insertedCustomer = customerService.create(requestCustomer);

		// then
		Assertions.assertThat(insertedCustomer).isNotNull();
		MatcherAssert.assertThat(insertedCustomer, Matchers.samePropertyValuesAs(requestCustomer));
	}

}