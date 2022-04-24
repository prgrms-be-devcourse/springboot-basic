package org.prgms.springbootbasic.customer.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.customer.repository.customer.CustomerRepository;
import org.prgms.springbootbasic.voucher.entity.VoucherType;
import org.prgms.springbootbasic.voucher.repository.voucher.VoucherRepository;
import org.prgms.springbootbasic.voucher.service.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class CustomerServiceImplTest {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;

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

	@DisplayName("createVoucher 성공 Test")
	@Test
	void create_Voucher_pass_test() {
		customerService.createCustomer("Jack", "Jack@gmail.com");
		customerService.createCustomer("alice", "alice@gmail.com");
		customerService.createCustomer("John", "John@gmail.com");

		assertThat(customerRepository.getTotalCustomerCount()).isEqualTo(3);
	}

	@DisplayName("createVoucher 실패 테스트 email은 Unique 해야함")
	@Test
	public void create_Voucher_fail_test() throws Exception {
		// given
		customerService.createCustomer("Jack", "Jack@gmail.com");
		// Voucher 인자에 허가되지 않은 값을 입력했을 때
		assertThrows(DataAccessException.class, () -> customerService.createCustomer("Jack", "Jack@gmail.com"));
	}


}
