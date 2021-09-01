package org.programmers.kdt.customer.repository;

import org.junit.jupiter.api.*;
import org.programmers.kdt.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("local")
@SpringBootTest
class JdbcCustomerRepositoryTest {
	List<Customer> list = new ArrayList<>();

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	DataSource dataSource;

	@Test
	@Order(1)
	@DisplayName("새로운 고객을 DB에 추가")
	void insert() {
		Customer customer = new Customer(UUID.randomUUID(), "JDBC-Test", Math.random() + "@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		Customer customer2 = new Customer(UUID.randomUUID(), "JDBC-Test2", Math.random() + "@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		Customer customer3 = new Customer(UUID.randomUUID(), "JDBC-Test3", Math.random() + "@gmail.com", LocalDateTime.now(), LocalDateTime.now());

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
		assertThat(customerRepository.findById(customer.getCustomerId())).isEqualTo(Optional.of(customer));
	}

	@Test
	@Order(3)
	@DisplayName("이름으로 회원 찾기")
	void findByName() {
		Customer customer = list.get(1);
		assertThat(customerRepository.findByName(customer.getName())).contains(customer);
	}

	@Test
	@Order(4)
	@DisplayName("이메일로 회원 찾기")
	void findByEmail() {
		Customer customer = list.get(2);
		assertThat(customerRepository.findByEmail(customer.getEmail())).isEqualTo(Optional.of(customer));
	}

	@Test
	@Order(5)
	@DisplayName("모든 회원 조회")
	void findAll() {
		List<Customer> allCustomer = customerRepository.findAll();
		assertThat(allCustomer).hasSizeGreaterThanOrEqualTo(list.size());
		assertThat(allCustomer).contains(list.get(0));
		assertThat(allCustomer).contains(list.get(1));
		assertThat(allCustomer).contains(list.get(2));

	}

	@Test
	@Order(6)
	@DisplayName("블랙리스트 등록하기")
	void registerToBlacklist() {
		Customer customer = list.get(0);
		Customer customer2 = list.get(1);
		assertThat(customerRepository.registerToBlacklist(customer)).isEqualTo(customer);
		assertThat(customerRepository.registerToBlacklist(customer2)).isEqualTo(customer2);
	}

	@Test
	@Order(7)
	@DisplayName("블랙리스트에 등록된 모든 회원 조회")
	void findAllBlacklistCustomer() {
		List<Customer> allBlacklistCustomer = customerRepository.findAllBlacklistCustomer();
		// TODO : 테스트 전용 별도의 테이블을 만들어 기존의 테이블의 영향을 받지 않도록 하기
		assertThat(allBlacklistCustomer).hasSizeGreaterThanOrEqualTo(2);
		assertThat(allBlacklistCustomer).contains(list.get(0));
		assertThat(allBlacklistCustomer).contains(list.get(1));
		assertThat(allBlacklistCustomer).doesNotContain(list.get(2));
	}

	@Test
	@Order(8)
	@DisplayName("등록된 고객을 삭제")
	void deleteCustomer() {
		int beforeDelete = customerRepository.findAll().size();
		customerRepository.deleteCustomer(list.get(0));
		int afterDelete = customerRepository.findAll().size();
		assertThat(beforeDelete - afterDelete).isEqualTo(1);
		list.remove(0);
	}

	@Test
	@Order(9)
	@DisplayName("모든 고객 데이터 제거")
	void deleteAll() {
		customerRepository.deleteAll();
		assertThat(customerRepository.findAll()).hasSize(0);
	}
}