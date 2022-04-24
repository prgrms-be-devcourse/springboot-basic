package org.prgms.springbootbasic.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.repository.voucher.MemoryVoucherRepository;
import org.prgms.springbootbasic.voucher.repository.voucher.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.VoucherType;

class VoucherServiceTest {

	VoucherRepository voucherRepository;
	VoucherService voucherService;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
		voucherService = new VoucherService(voucherRepository);
	}

	@DisplayName("createVoucher 성공 Test")
	@Test
	void create_Voucher_pass_test() {
		voucherService.create(VoucherType.FIXEDAMOUNTVOUCHER.name(), 20000);
		voucherService.create(VoucherType.PERCENTDISCOUNTVOUCHER.name(), 30);
		voucherService.create(VoucherType.PERCENTDISCOUNTVOUCHER.name(), 40);

		assertEquals(3, voucherRepository.getTotalVoucherCount());
	}

	@DisplayName("createVoucher 실패 테스트")
	@Test
	public void create_Voucher_fail_test() throws Exception {
		// Voucher 인자에 허가되지 않은 값을 입력했을 때
		assertThrows(IllegalArgumentException.class, () -> voucherService.create("1", -1));
		assertThrows(IllegalArgumentException.class, () -> voucherService.create("2", -1));
		assertThrows(IllegalArgumentException.class, () -> voucherService.create("2", 101));
	}

}
