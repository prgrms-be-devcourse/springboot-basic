package com.programmers.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.order.config.TestConfig;
import com.programmers.order.domain.Customer;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.repository.customer.CustomerJdbcRepository;


@SpringJUnitConfig(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

	private final static boolean NOT_EMPTY = false;
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomerJdbcRepository customerRepository;

	private Customer newCustomer;

	@DisplayName("최초 딱 한번 실행 메소드")
	@BeforeAll
	void init() {
		newCustomer = new Customer(UUID.randomUUID()
				, "test-user"
				, "test@programmers.co.kr"
				, LocalDateTime.now());
	}

	@Order(1)
	@Test
	@DisplayName("application context loading test")
	void ContextLoadingTest() {
		// given
		// when
		// then
		Assertions.assertNotNull(applicationContext);
	}

	@Order(2)
	@Test
	@DisplayName("hikari connect test")
	void connectHikariTest() {
		//given
		//when
		//then
		MatcherAssert.assertThat(dataSource.getClass().getName(), Matchers.is("com.zaxxer.hikari.HikariDataSource"));

	}

	@Order(3)
	@Test
	@DisplayName("all delete")
	void DeleteAllTest() {
		//given
		//when
		customerRepository.deleteAll();
		List<Customer> customers = customerRepository.findAll();
		//then
		Assertions.assertEquals(customers.size(), 0);

	}

	@Order(4)
	@Test
	@DisplayName("insert test")
	void insertTest() {
		try {
			// given
			// when
			customerRepository.insert(newCustomer);
		} catch (BadSqlGrammarException e) {
			e.getSQLException().getErrorCode();
		}

		// then
		Optional<Customer> customer = customerRepository.findById(newCustomer.getCustomerId());
		MatcherAssert.assertThat(customer.isEmpty(), Matchers.is(NOT_EMPTY));
		MatcherAssert.assertThat(customer.get().getCustomerId(), Matchers.is(newCustomer.getCustomerId()));
	}

	@Order(5)
	@Test
	@DisplayName("update test")
	void updateTest() {
		//given

		Customer previousCustomer = customerRepository.findById(newCustomer.getCustomerId())
				.orElseThrow(() -> new RuntimeException("No data ..."));

		String updatedName = "update-name";
		CustomerDto.UpdateCustomer updateCustomer = new CustomerDto.UpdateCustomer(previousCustomer.getEmail(),
				updatedName);
		newCustomer.changeName(updateCustomer);

		//when
		Customer updatedCustomer = customerRepository.update(newCustomer);

		//then
		Assertions.assertNotNull(updatedCustomer);
		Assertions.assertNotEquals(updatedCustomer.getName(), previousCustomer.getName());
		Assertions.assertEquals(updatedCustomer.getName(), updatedName);
	}

	@Order(6)
	@Test
	@DisplayName("findAll test")
	void findAllTest() {
		//given

		//when
		List<Customer> customers = customerRepository.findAll();

		//then
		Assertions.assertEquals(customers.size(), 1);
		// MatcherAssert.assertThat(customers.get(0), Matchers.samePropertyValuesAs(newCustomer));
		MatcherAssert.assertThat(customers.get(0).getCustomerId(), Matchers.is(newCustomer.getCustomerId()));
	}

	@Order(7)
	@Test
	@DisplayName("count query test")
	void countQueryTest() {
		//given

		//when
		int count = customerRepository.count();

		//then
		Assertions.assertEquals(count, 1);
	}
}
