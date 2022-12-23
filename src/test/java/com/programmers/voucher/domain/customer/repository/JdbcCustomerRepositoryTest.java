package com.programmers.voucher.domain.customer.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;

@SpringJUnitConfig
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

	@Autowired
	JdbcCustomerRepository customerRepository;

	@Configuration
	@ComponentScan(basePackages = "com.programmers.voucher.domain.customer")
	static class Config {
		@Bean
		DataSource embeddedDataSource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("schema.sql")
				.build();
		}

		@Bean
		NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
			return new NamedParameterJdbcTemplate(embeddedDataSource());
		}

	}

	@AfterEach
	void clearRepository() {
		customerRepository.deleteAll();
	}

	@Test
	@DisplayName("고객의 올바른 값이 들어오면 정상적으로 저장된다.")
	void saveTest() {
		Customer customer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());

		Customer createdCustomer = customerRepository.save(customer);

		assertThat(createdCustomer).isEqualTo(customer);
	}

	@Test
	void findByIdTest() {
		Customer customer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		Customer createdCustomer = customerRepository.save(customer);

		Customer findCustomer = customerRepository.findById(createdCustomer.getCustomerId());

		assertThat(findCustomer.toString()).isEqualTo(createdCustomer.toString());
	}

	@Test
	void updateTest() {
		Customer customer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		Customer updateCustomer = new Customer(customer.getCustomerId(), LocalDateTime.now(), CustomerType.BLACKLIST,
			LocalDateTime.now());
		customerRepository.save(customer);

		Customer updatedCustomer = customerRepository.update(updateCustomer);

		assertThat(updatedCustomer)
			.usingRecursiveComparison()
			.isEqualTo(updateCustomer);
	}

	@Test
	void deleteByIdTest() {
		UUID deleteId1 = UUID.randomUUID();
		UUID deleteId2 = UUID.randomUUID();
		Customer normalCustomer = new Customer(deleteId1, LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		Customer blacklistCustomer = new Customer(deleteId2, LocalDateTime.now(), CustomerType.BLACKLIST,
			LocalDateTime.now());
		customerRepository.save(normalCustomer);
		customerRepository.save(blacklistCustomer);
		List<Customer> beforeDelete = customerRepository.findAll();

		customerRepository.deleteById(deleteId1);
		customerRepository.deleteById(deleteId2);
		List<Customer> afterDelete = customerRepository.findAll();

		assertThat(afterDelete.size()).isEqualTo(beforeDelete.size() - 2);
	}

	@Test
	@DisplayName("모든 고객 조회를 성공한다.")
	void findAllTest() {
		Customer normalCustomer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		Customer blacklistCustomer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.BLACKLIST,
			LocalDateTime.now());
		List<Customer> beforeSave = customerRepository.findAll();

		customerRepository.save(normalCustomer);
		customerRepository.save(blacklistCustomer);
		List<Customer> afterSave = customerRepository.findAll();

		assertThat(afterSave.size()).isEqualTo(beforeSave.size() + 2);
	}

	@Test
	@DisplayName("고객들 중 블랙리스트들 조회를 성공한다.")
	void findAllBlacklistTest() {
		Customer normalCustomer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		Customer blacklistCustomer = new Customer(UUID.randomUUID(), LocalDateTime.now(), CustomerType.BLACKLIST,
			LocalDateTime.now());
		List<Customer> beforeSave = customerRepository.findAllBlacklist();

		customerRepository.save(normalCustomer);
		customerRepository.save(blacklistCustomer);
		List<Customer> afterSave = customerRepository.findAllBlacklist();

		assertThat(afterSave.size()).isEqualTo(beforeSave.size() + 1);
	}
}