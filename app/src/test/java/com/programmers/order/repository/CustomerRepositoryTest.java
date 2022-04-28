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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.programmers.order.domain.Customer;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.repository.customer.CustomerJdbcRepository;
import com.programmers.order.repository.customer.CustomerRepository;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerRepositoryTest {

	private final static boolean NOT_EMPTY = false;

	@Configuration
	@ComponentScan(basePackages = {"com.programmers.order"})
	static class Config {

		@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
		return new TransactionTemplate(platformTransactionManager);
	}

	@Bean
	public CustomerRepository customerRepository(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new CustomerJdbcRepository(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
	}

}

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomerJdbcRepository customerRepository;

	private Customer newCustomer;

	// private EmbeddedMysql embeddedMysql;

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
	public void ContextLoadingTest() {
		// given
		// when
		// then
		Assertions.assertNotNull(applicationContext);
	}

	@Order(2)
	@Test
	@DisplayName("hikari connect test")
	public void connectHikariTest() {
		//given
		//when
		//then
		MatcherAssert.assertThat(dataSource.getClass().getName(), Matchers.is("com.zaxxer.hikari.HikariDataSource"));

	}

	@Order(3)
	@Test
	@DisplayName("all delete")
	public void DeleteAllTest() {
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
	public void insertTest() {
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
	public void findAllTest() {
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
	public void countQueryTest() {
		//given

		//when
		int count = customerRepository.count();

		//then
		Assertions.assertEquals(count, 1);
	}
}
