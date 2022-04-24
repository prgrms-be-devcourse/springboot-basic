package org.prgms.springbootbasic.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.repository.voucher.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class VoucherServiceImplTest {

	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	VoucherServiceImpl voucherService;

	@Configuration
	@ComponentScan(basePackages = {
		"org.prgms.springbootbasic.config",
		"org.prgms.springbootbasic.voucher",
	})
	static class testConfig {
	}

	@BeforeEach
	void setUp() {
		voucherRepository.deleteVouchers();
	}

	@DisplayName("createVoucher 성공 Test")
	@Test
	void create_Voucher_pass_test() {
		voucherService.createVoucher(VoucherType.FIXEDAMOUNTVOUCHER.name(), 20000);
		voucherService.createVoucher(VoucherType.PERCENTDISCOUNTVOUCHER.name(), 30);
		voucherService.createVoucher(VoucherType.PERCENTDISCOUNTVOUCHER.name(), 40);

		assertEquals(3, voucherRepository.getTotalVoucherCount());
	}

	@DisplayName("createVoucher 실패 테스트")
	@Test
	public void create_Voucher_fail_test() throws Exception {
		// Voucher 인자에 허가되지 않은 값을 입력했을 때
		assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher("1", -1));
		assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher("2", -1));
		assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher("2", 101));
	}

}
