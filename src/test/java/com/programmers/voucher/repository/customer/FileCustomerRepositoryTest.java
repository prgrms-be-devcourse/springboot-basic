package com.programmers.voucher.repository.customer;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.domain.customer.CustomerType;

class FileCustomerRepositoryTest {

	CustomerRepository repository = new FileCustomerRepository();

	@Test
	@DisplayName("블랙리스트를 조회하면 파일에서 조회를 성공한다.")
	void findAllBlacklist() {
		Customer normal = new Customer(UUID.randomUUID(), CustomerType.BLACKLIST);
		Customer blacklist = new Customer(UUID.randomUUID(), CustomerType.NORMAL);

	}
}