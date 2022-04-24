package org.prgms.springbootbasic.customer.repository.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;

	@Configuration
	@ComponentScan(basePackages = {
		"org.prgms.springbootbasic.config",
		"org.prgms.springbootbasic.customer",
	})
	static class testConfig {
	}

	@BeforeEach
	void setUp() {
		customerRepository.deleteCustomers();
	}

	@DisplayName("repository 저장(save) 성공 테스트")
	@Test
	void save_pass_test() {
		//given
		Customer customer = new Customer("hani", "hani@gmail.com");
		//when
		final UUID savedUUID = customerRepository.save(customer);
		final Customer findCustomer = customerRepository.findById(savedUUID);
		// then
		assertThat(findCustomer).isNotNull();
		assertThat(findCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
	}

	@DisplayName("repository save 실패 테스트")
	@Test
	void save_fail_test() {
		// null을 저장할 수는 없다.
		assertThrows(IllegalArgumentException.class, () -> customerRepository.save(null));
	}

	@DisplayName("findById 실패 테스트 - 존재하지 않는 ID를 입력한 경우")
	@Test
	void findById_fail_test_invalid_ID() {
		assertThrows(DataAccessException.class, () -> customerRepository.findById(UUID.randomUUID()));
	}
}
