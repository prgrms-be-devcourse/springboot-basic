package study.dev.spring.customer.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import study.dev.spring.customer.fixture.CustomerFixture;
import study.dev.spring.customer.infrastructure.JdbcCustomerRepository;
import study.dev.spring.global.support.DataSourceTestSupport;

@DisplayName("[CustomerRepository 테스트] - Domain Layer")
class CustomerRepositoryTest extends DataSourceTestSupport {

	private final CustomerRepository customerRepository;

	public CustomerRepositoryTest(@Autowired DataSource dataSource) {
		this.customerRepository = new JdbcCustomerRepository(dataSource);
	}

	@Test
	@DisplayName("[고객을 저장한다]")
	void save() {
		//given
		String uuid = UUID.randomUUID().toString();
		Customer customer = new Customer(uuid, "name");

		//when
		Customer actual = customerRepository.save(customer);

		//then
		List<Customer> allCustomers = customerRepository.findAll();
		assertThat(actual).isEqualTo(customer);
		assertThat(allCustomers).hasSize(1);
	}

	@Test
	@DisplayName("[모든 고객을 조회한다]")
	void findAll() {
		//given
		Customer customer = CustomerFixture.getCustomer();
		customerRepository.save(customer);

		//when
		List<Customer> actual = customerRepository.findAll();

		//then
		assertThat(actual).hasSize(1);

		assertCustomer(actual.get(0), customer);
	}

	@Nested
	@DisplayName("[아이디로 고객을 조회한다]")
	class findById {

		@Test
		@DisplayName("[조회에 성공한다]")
		void success() {
			//given
			Customer customer = CustomerFixture.getCustomer();
			customerRepository.save(customer);

			//when
			Optional<Customer> actual = customerRepository.findById(customer.getUuid());

			//then
			assertThat(actual).isPresent();

			Customer actualCustomer = actual.get();
			assertCustomer(customer, actualCustomer);
		}

		@Test
		@DisplayName("[아이디가에 해당하는 고객이 존재하지 않아 빈 값을 반환한다]")
		void emptyValue() {
			//given
			Customer customer = CustomerFixture.getCustomer();

			//when
			Optional<Customer> actual = customerRepository.findById(customer.getUuid());

			//then
			assertThat(actual).isNotPresent();
		}
	}

	@Test
	@DisplayName("[아이디 리스트에 포함된 모든 고객을 조회한다]")
	void findByIds() {
		//given
		List<Customer> customers = CustomerFixture.getCustomers();
		customers.forEach(customerRepository::save);

		List<String> ids = customers.stream().map(Customer::getUuid).toList();

		//when
		List<Customer> actual = customerRepository.findByIds(ids);

		//then
		assertThat(actual).hasSameSizeAs(customers);
		for (int i = 0; i < actual.size(); i++) {
			assertCustomer(actual.get(i), customers.get(i));
		}
	}

	private void assertCustomer(Customer actual, Customer expected) {
		assertAll(
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getUuid()).isEqualTo(expected.getUuid())
		);
	}
}