package org.prgms.springbootbasic.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.repository.MemoryVoucherRepository;
import org.prgms.springbootbasic.voucher.repository.VoucherRepository;

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


	}
}
