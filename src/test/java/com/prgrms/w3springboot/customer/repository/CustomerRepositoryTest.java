package com.prgrms.w3springboot.customer.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.prgrms.w3springboot.customer.Customer;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {
	private static final UUID NEW_CUSTOMER_ID = UUID.randomUUID();
	private static final UUID EXISTING_CUSTOMER_ID = UUID.fromString("9cbb3d0a-158a-11ec-82a8-0242ac130003");

	private static EmbeddedMysql embeddedMysql;

	@Autowired
	private CustomerRepository customerRepository;

	@BeforeAll
	void setup() {
		MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
			.withCharset(UTF8)
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asia/Seoul")
			.build();

		embeddedMysql = anEmbeddedMysql(mysqlConfig)
			.addSchema("test-devcourse", classPathScript("schema.sql"))
			.start();
	}

	@AfterAll
	void cleanup() {
		embeddedMysql.stop();
	}

	@Test
	@DisplayName("고객을 추가할 수 있다.")
	void testInsert() {
		Customer newCustomer = new Customer(NEW_CUSTOMER_ID, "test-user", "test-user@gmail.com", LocalDateTime.now());
		customerRepository.insert(newCustomer);

		Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
		List<Customer> all = customerRepository.findAll();

		assertThat(all).hasSize(4);
		assertThat(retrievedCustomer)
			.isPresent()
			.get()
			.hasFieldOrPropertyWithValue("customerId", NEW_CUSTOMER_ID);
	}

	@Test
	@DisplayName("고객을 수정할 수 있다.")
	void testUpdate() {
		Customer updateCustomer = new Customer(EXISTING_CUSTOMER_ID, "update-user", "upodate-user@gmail.com",
			LocalDateTime.now());
		customerRepository.update(updateCustomer);

		Optional<Customer> retrievedCustomer = customerRepository.findById(updateCustomer.getCustomerId());

		assertThat(retrievedCustomer)
			.isPresent()
			.get()
			.hasFieldOrPropertyWithValue("customerId", EXISTING_CUSTOMER_ID);
	}

	@DisplayName("ID로 고객을 조회한다.")
	@Test
	void testFindById() {
		Optional<Customer> foundCustomer = customerRepository.findById(EXISTING_CUSTOMER_ID);

		assertThat(foundCustomer).isPresent();
	}

	@Test
	@DisplayName("이름으로 고객을 조회한다.")
	void testFindByName() {
		Optional<Customer> customer = customerRepository.findByName("test01");
		assertThat(customer).isPresent();

		Optional<Customer> unknown = customerRepository.findByName("unknown-user");
		assertThat(unknown).isNotPresent();
	}

	@DisplayName("이메일로 고객을 조회한다.")
	@Test
	void testFindByEmail() {
		String email = "test01@gmail.com";

		Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

		assertThat(foundCustomer).isPresent();
	}

	@Test
	@DisplayName("전체 고객을 조회한다.")
	void testFindAll() {
		List<Customer> customers = customerRepository.findAll();

		assertThat(customers).isNotEmpty();
	}

	@Configuration
	static class Config {
		@Bean
		public DataSource dataSource() {
			return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:2215/test-devcourse")
				.username("test")
				.password("test1234!")
				.type(HikariDataSource.class)
				.build();
		}

		@Bean
		public JdbcTemplate jdbcTemplate(DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}

		@Bean
		public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
			return new NamedParameterJdbcTemplate(jdbcTemplate);
		}

		@Bean
		public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			return new CustomerNamedJDBCRepository(namedParameterJdbcTemplate);
		}
	}
}
