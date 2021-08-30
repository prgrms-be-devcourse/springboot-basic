package org.programmers.kdt.customer.repository;

import org.junit.jupiter.api.*;
import org.programmers.kdt.customer.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {

	List<Customer> list = new ArrayList<>();

	CustomerRepository customerRepository = new FileCustomerRepository();

	@AfterAll
	void tearDown() {
		for (Customer customer : list) {
			customerRepository.deleteCustomer(customer);
		}
	}

	@Test
	@Order(1)
	@DisplayName("회원 추가")
	void insert() {
		Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		Customer customer3 = new Customer(UUID.randomUUID(), "test3", "test3@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		list.add(customer);
		list.add(customer2);
		list.add(customer3);

		assertThat(customerRepository.insert(customer)).isEqualTo(customer);
		assertThat(customerRepository.insert(customer2)).isEqualTo(customer2);
		assertThat(customerRepository.insert(customer3)).isEqualTo(customer3);
	}

	@Test
	@Order(2)
	@DisplayName("ID로 회원 찾기")
	void findById() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		assertThat(customerRepository.findById(customer.getCustomerId())).isEqualTo(Optional.of(customer));
		assertThat(customerRepository.findById(customer.getCustomerId())).isNotEqualTo(Optional.of(customer2));
	}

	@Test
	@Order(3)
	@DisplayName("이름으로 회원 찾기")
	void findByName() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		assertThat(customerRepository.findByName(customer.getName())).contains(customer);
		assertThat(customerRepository.findByName(customer.getName())).doesNotContain(customer2);
		assertThat(customerRepository.findByName(customer.getName())).hasSizeGreaterThanOrEqualTo(1);
	}

	@Test
	@Order(4)
	@DisplayName("Email로 회원 찾기")
	void findByEmail() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		assertThat(customerRepository.findByEmail(customer.getEmail())).isEqualTo(Optional.of(customer));
		assertThat(customerRepository.findByEmail(customer.getEmail())).isNotEqualTo(Optional.of(customer2));
	}

	@Test
	@Order(5)
	@DisplayName("모든 회원 찾기")
	void findAll() {
		// TODO : 기존에 읽는 파일에 영향을 받고 있음. 테스트시에 기존 파일의 영향을 제거해야 함.
		assertThat(customerRepository.findAll()).hasSizeGreaterThanOrEqualTo(3);
	}

	@Test
	@Order(6)
	@DisplayName("Blacklist에 등록하기")
	void registerToBlacklist() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		Customer customer3 = list.get(2);
		assertThat(customerRepository.registerToBlacklist(customer)).isEqualTo(customer);
		assertThat(customerRepository.registerToBlacklist(customer2)).isEqualTo(customer2);
		assertThat(customerRepository.registerToBlacklist(customer2)).isNotEqualTo(customer3);
	}

	@Test
	@Order(7)
	@DisplayName("Blacklist에 올라간 모든 고객 조회")
	void findAllBlacklistCustomer() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		Customer customer3 = list.get(2);
		assertThat(customerRepository.findAllBlacklistCustomer()).contains(customer);
		assertThat(customerRepository.findAllBlacklistCustomer()).contains(customer2);
		assertThat(customerRepository.findAllBlacklistCustomer()).doesNotContain(customer3);
	}

	@Test
	@Order(8)
	@DisplayName("고객 정보 삭제")
	void deleteCustomer() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		Customer customer3 = list.get(2);

		assertThat(customerRepository.findById(customer.getCustomerId())).isPresent();
		customerRepository.deleteCustomer(customer);
		assertThat(customerRepository.findById(customer.getCustomerId())).isEmpty();
	}
}