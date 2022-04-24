package org.prgms.springbootbasic.voucher.repository.vouchertype;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.entity.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class JdbcVoucherTypeRepositoryTest {

	@Configuration
	@ComponentScan(basePackages = {
		"org.prgms.springbootbasic.config",
		"org.prgms.springbootbasic.voucher",
	})
	static class testConfig {
	}

	@Autowired
	VoucherTypeRepository voucherTypeRepository;

	@DisplayName("voucherTypeRepository 생성 테스트 - autowired 되어야 한다.")
	@Test
	void create_VoucherTypeRepository_test() {
		assertThat(voucherTypeRepository).isNotNull();
	}

	@DisplayName("VoucerType을 Save할 수 있어야 한다.")
	@Disabled // 추가로 테스트하려면 VoucherType을 더 정의해야
	@Test
	void save_pass_test() {
		//given
		final VoucherType fixedSavedType = voucherTypeRepository.save(VoucherType.FIXEDAMOUNTVOUCHER);
		// when
		final UUID uuidByName = voucherTypeRepository.findUUIDByName(fixedSavedType.name());
		System.out.println(uuidByName);
		// then
		assertThat(uuidByName).isNotNull();

		//given
		final VoucherType percentSavedType = voucherTypeRepository.save(VoucherType.PERCENTDISCOUNTVOUCHER);
		// when
		final UUID uuidPercent = voucherTypeRepository.findUUIDByName(percentSavedType.name());
		System.out.println(uuidPercent);

		// then
		assertThat(uuidPercent).isNotNull();

	}

	@DisplayName("VoucherType의 UUID는 이름(name)으로 찾을 수 있어야 한다.")
	@Test
	void findIdByName_Test() {
		// when
		final UUID fixUUID = voucherTypeRepository.findUUIDByName(VoucherType.FIXEDAMOUNTVOUCHER.name());
		// then
		assertThat(fixUUID).isNotNull();

		// when
		final UUID percentUUID = voucherTypeRepository.findUUIDByName(VoucherType.PERCENTDISCOUNTVOUCHER.name());
		// then
		assertThat(percentUUID).isNotNull();

	}

	@DisplayName("잘못된 VoucherTypename을 입력하면 DataAccessException이 발생한다")
	@Test
	void findIdByName_fail_test() {
		assertThrows(DataAccessException.class, () -> voucherTypeRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> voucherTypeRepository.findUUIDByName(null));
	}

	@DisplayName("VoucherType은 voucherTypeId으로 찾을 수 있어야 한다.")
	@Test
	void findById_pass_test() {

		// given
		final UUID fixUUID = voucherTypeRepository.findUUIDByName(VoucherType.FIXEDAMOUNTVOUCHER.name());
		// whenR함
		final VoucherType fixVoucherType = voucherTypeRepository.findById(fixUUID);
		// then
		assertThat(fixVoucherType).isNotNull();
		assertThat(fixVoucherType).isEqualTo(VoucherType.FIXEDAMOUNTVOUCHER);

		// // given
		final UUID percentUUID = voucherTypeRepository.findUUIDByName(VoucherType.PERCENTDISCOUNTVOUCHER.name());
		// // when
		final VoucherType percentVoucherType = voucherTypeRepository.findById(percentUUID);
		// // then
		assertThat(percentVoucherType).isNotNull();
		assertThat(percentVoucherType).isEqualTo(VoucherType.PERCENTDISCOUNTVOUCHER);
	}

	@DisplayName("잘못된 voucherTypeId 입력하면 DataAccessException이 발생한다")
	@Test
	void findById_fail_test() {
		assertThrows(DataAccessException.class, () -> voucherTypeRepository.findUUIDByName("asdf"));
		assertThrows(DataAccessException.class, () -> voucherTypeRepository.findUUIDByName(null));
	}

}
