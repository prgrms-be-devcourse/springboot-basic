package org.prgms.springbootbasic.customer.repository.customerstatus;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class JdbcCustomerStatusRepositoryTest {

	@Configuration
	@ComponentScan(basePackages = {
		"org.prgms.springbootbasic.config",
		"org.prgms.springbootbasic.customer",
	})
	static class testConfig {
	}

	@Autowired
	CustomerStatusRepository customerStatusRepository;

	@DisplayName("customerStatusRepository 생성 테스트 - autowired 되어야 한다.")
	@Test
	void create_VoucherTypeRepository_test() {
		assertThat(customerStatusRepository).isNotNull();
	}


	@DisplayName("CustomerStatus UUID는 이름(name)으로 찾을 수 있어야 한다.")
	@Test
	void findIdByName_Test() {
		// when
		final UUID availableStatus = customerStatusRepository.findUUIDByName(CustomerStatus.AVAILABLE.name());
		// then
		assertThat(availableStatus).isNotNull();

		// when
		final UUID blockedStatus = customerStatusRepository.findUUIDByName(CustomerStatus.BLOCKED.name());
		// then
		assertThat(blockedStatus).isNotNull();

	}

	@DisplayName("잘못된 status name을 입력하면 DataAccessException이 발생한다")
	@Test
	void findIdByName_fail_test() {
		assertThrows(DataAccessException.class, () -> customerStatusRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> customerStatusRepository.findUUIDByName(null));
	}

	@DisplayName("CustomerStatus는 status UUIDfh 찾을 수 있어야 한다.")
	@Test
	void findById_pass_test() {

		// given
		final UUID availableStatusUUID = customerStatusRepository.findUUIDByName(CustomerStatus.AVAILABLE.name());
		// whenR함
		final CustomerStatus availableStatus = customerStatusRepository.findById(availableStatusUUID);
		// then
		assertThat(availableStatus).isNotNull();
		assertThat(availableStatus).isEqualTo(CustomerStatus.AVAILABLE);

		// // given
		final UUID blockedStatusUUID = customerStatusRepository.findUUIDByName(CustomerStatus.BLOCKED.name());
		// // when
		final CustomerStatus blockedStatus = customerStatusRepository.findById(blockedStatusUUID);
		// // then
		assertThat(blockedStatus).isNotNull();
		assertThat(blockedStatus).isEqualTo(CustomerStatus.BLOCKED);
	}

	@DisplayName("잘못된 voucherTypeId 입력하면 DataAccessException이 발생한다")
	@Test
	void findById_fail_test() {
		assertThrows(DataAccessException.class, () -> customerStatusRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> customerStatusRepository.findUUIDByName(null));
	}
}
