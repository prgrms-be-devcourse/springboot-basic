package org.prgms.springbootbasic.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.vo.Voucher;

class VoucherTypeTest {

	@DisplayName("type에 따라 Voucher 생성 성공 테스트")
	@Test
	void createVoucher_pass_test() {
		VoucherType type = VoucherType.getVoucherType("1");
		Voucher voucher = type.createVoucher(1000L);

		assertEquals("FixedAmountVoucher",voucher.getVoucherType());

		VoucherType type2 = VoucherType.getVoucherType("2");
		Voucher voucher2 = type2.createVoucher(50);

		assertEquals("PercentDiscountVoucher",voucher2.getVoucherType());
	}

	@DisplayName("createVoucher 생성 실패 테스트")
	@Test
	void createVoucher_fail_test() {
		// button을 1,2값이 아닌 다른 값을 입력했을 때
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType("3"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType("a"));

		// Voucher를 생성하는데 들어가는 인자에 음수값을 넣었을 때
		VoucherType fixedVoucherType = VoucherType.getVoucherType("1");
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(-10L));
		VoucherType percentVoucherType = VoucherType.getVoucherType("2");
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(-10));

		// PercentVoucher를 생성할 떄 1~100 범위를 넘어설 때
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(200));

	}
}
