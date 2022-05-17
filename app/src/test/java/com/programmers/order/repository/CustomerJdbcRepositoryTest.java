package com.programmers.order.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.javafaker.Faker;
import com.programmers.order.config.TestJdbcConfig;
import com.programmers.order.domain.Customer;

@SpringJUnitConfig(TestJdbcConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	private Faker faker = new Faker();
	private Customer customer;

	@BeforeAll
	void init() {
		customer = Customer.builder()
				.customerId(UUID.randomUUID())
				.email(faker.name().username() + "@programmers.co.kr")
				.name(faker.name().fullName()+"asdhasbdhabshdbjhasbdhjbahjsdbhj")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	@Rollback(value = true)
	@DisplayName("고객 insert query")
	@Test
	void insert() {
		// given

		// when
		Customer inserted = customerRepository.insert(customer);
		// then

		Assertions.assertThat(inserted).isNotNull();
		MatcherAssert.assertThat(inserted, Matchers.samePropertyValuesAs(customer));

	}
}