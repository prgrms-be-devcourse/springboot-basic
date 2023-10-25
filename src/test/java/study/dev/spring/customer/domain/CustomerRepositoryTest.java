package study.dev.spring.customer.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import study.dev.spring.customer.infrastructure.JdbcCustomerRepository;
import study.dev.spring.global.support.DataSourceTestSupport;

@DisplayName("[CustomerRepository 테스트] - Domain Layer")
class CustomerRepositoryTest extends DataSourceTestSupport {

	private final CustomerRepository customerRepository;

	public CustomerRepositoryTest(@Autowired DataSource dataSource) {
		this.customerRepository = new JdbcCustomerRepository(dataSource);
	}

	@Test
	@DisplayName("[모든 고객을 조회한다]")
	void findAll() {
		//when
		List<Customer> actual = customerRepository.findAll();

		//then
		assertThat(actual).hasSize(1);
		Customer actualCustomer = actual.get(0);
		assertAll(
			() -> assertThat(actualCustomer.getName()).isEqualTo("name"),
			() -> assertThat(actualCustomer.getUuid()).isEqualTo("uuid")
		);
	}
}