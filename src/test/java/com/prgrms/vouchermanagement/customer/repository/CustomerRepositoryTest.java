package com.prgrms.vouchermanagement.customer.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.vouchermanagement.customer.domain.Customer;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {
	// TODO : test data 를 BeforeEach, BeforeAll 에서 save 하는 것으로 변경 후 afterEach 를 작성하여 동일 결과를 보장할 수 있도록 해야함

	@Autowired
	CustomerRepository customerRepository;

	private UUID existingId;

	@BeforeEach
	public void setup() {
		existingId = UUID.fromString("98bcb05e-c64b-11ec-8ac0-86fc60ea758b");
	}

	@Test
	@DisplayName("실제로 저장했던 데이터 개수와 같은 사이즈다")
	void given_repository_when_compareSize_thenSuccess() {

		List<Customer> customers = customerRepository.findAll();

		assertThat(customers.size(), is(3));
	}

	@Test
	@DisplayName("중복 ID 를 갖는 고객을 새로 저장하려고 할 경우 DuplicatedKeyException 을 던진다")
	void given_id_when_findVoucher_thenSuccess() {
		Customer customer = new Customer(existingId, "hello", "abc@naver.com", LocalDateTime.now());

		Assertions.assertThrows(DuplicateKeyException.class, () -> customerRepository.insert(customer));
	}

	@Test
	@DisplayName("디비로부터 가져온 고객 정보를 사용하여 정상적으로 직렬화된 고객 객체를 생성한다")
	void given_usingTestData_when_fetchingVoucherData_thenMakingVoucherFromDataSuccess() {
		Optional<Customer> customerOptional = customerRepository.findById(existingId);

		assertThat(customerOptional.isPresent(), is(true));
		Customer customer = customerOptional.get();

		assertThat(customer.getCustomerId(), is(existingId));
		assertThat(customer.getName(), is("tester00"));
	}

	@Test
	@DisplayName("데이터를 저장하고 ID 를 사용해서 찾아온 후 ID를 사용해 데이터를 삭제할 수 있다")
	void given_repositoryAndData_When_saveData_thenFindingThatDataSuccess() {
		UUID id = UUID.randomUUID();
		Customer customer = new Customer(id, "hello", "abc@naver.com", LocalDateTime.now());

		customerRepository.insert(customer);

		Optional<Customer> customerOptional = customerRepository.findById(id);

		assertThat(customerOptional.isPresent(), is(true));
		assertThat(customerOptional.get().getCustomerId(), is(customer.getCustomerId()));

		long deleted = customerRepository.deleteById(id);

		assertThat(deleted, is(1L));

		Optional<Customer> deletedCustomer = customerRepository.findById(id);

		assertThat(deletedCustomer.isPresent(), is(false));
	}

}