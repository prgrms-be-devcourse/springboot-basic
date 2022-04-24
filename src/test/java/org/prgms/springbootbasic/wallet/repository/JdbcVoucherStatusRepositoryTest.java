package org.prgms.springbootbasic.wallet.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.wallet.entity.VoucherStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class JdbcVoucherStatusRepositoryTest {

	@Configuration
	@ComponentScan(basePackages = {
		"org.prgms.springbootbasic.config",
		"org.prgms.springbootbasic.wallet",
	})
	static class testConfig {
	}

	@Autowired
	VoucherStatusRepository voucherStatusRepository;

	@DisplayName("customerStatusRepository 생성 테스트 - autowired 되어야 한다.")
	@Test
	void create_VoucherTypeRepository_test() {
		assertThat(voucherStatusRepository).isNotNull();
	}


	@DisplayName("CustomerStatus UUID는 이름(name)으로 찾을 수 있어야 한다.")
	@Test
	void findIdByName_Test() {
		// when
		final UUID availableStatus = voucherStatusRepository.findUUIDByName(VoucherStatus.AVAILABLE.name());
		// then
		assertThat(availableStatus).isNotNull();

		// when
		final UUID usedStatus = voucherStatusRepository.findUUIDByName(VoucherStatus.USED.name());
		// then
		assertThat(usedStatus).isNotNull();

	}

	@DisplayName("잘못된 status name을 입력하면 DataAccessException이 발생한다")
	@Test
	void findIdByName_fail_test() {
		assertThrows(DataAccessException.class, () -> voucherStatusRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> voucherStatusRepository.findUUIDByName(null));
	}

	@DisplayName("CustomerStatus는 status UUID로 찾을 수 있어야 한다.")
	@Test
	void findById_pass_test() {

		// given
		final UUID availableStatusUUID = voucherStatusRepository.findUUIDByName(VoucherStatus.AVAILABLE.name());
		// whenR함
		final VoucherStatus availableStatus = voucherStatusRepository.findById(availableStatusUUID);
		// then
		assertThat(availableStatus).isNotNull();
		assertThat(availableStatus).isEqualTo(VoucherStatus.AVAILABLE);

		// // given
		final UUID blockedStatusUUID = voucherStatusRepository.findUUIDByName(VoucherStatus.USED.name());
		// // when
		final VoucherStatus blockedStatus = voucherStatusRepository.findById(blockedStatusUUID);
		// // then
		assertThat(blockedStatus).isNotNull();
		assertThat(blockedStatus).isEqualTo(VoucherStatus.USED);
	}

	@DisplayName("잘못된 voucherTypeId 입력하면 DataAccessException이 발생한다")
	@Test
	void findById_fail_test() {
		assertThrows(DataAccessException.class, () -> voucherStatusRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> voucherStatusRepository.findUUIDByName(null));
	}
}
