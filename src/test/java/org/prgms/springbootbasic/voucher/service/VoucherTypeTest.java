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
		//given
		VoucherType type = VoucherType.getVoucherType("1");
		//when
		Voucher voucher = type.createVoucher(1000L);
		//then
		assertEquals("FixedAmountVoucher", voucher.getVoucherType());

		VoucherType type2 = VoucherType.getVoucherType("2");
		Voucher voucher2 = type2.createVoucher(50);

		assertEquals("PercentDiscountVoucher", voucher2.getVoucherType());
	}

	@DisplayName("정해진 Button(1,2) 값이 아닌 다른 값을 입력했을 때 IllegalArgumentException 발생")
	@Test
	void not_allowed_button_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType("3"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType("a"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType(""));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.getVoucherType(null));

	}

	@DisplayName("FixedAMountvoucher를 생성할 때 인자(discountAmount)허가되지 않은 값을 넣었을 때 실패 테스트")
	@Test
	void not_allowed_FixedVoucher_param() {
		VoucherType fixedVoucherType = VoucherType.getVoucherType("1");
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(-10L));
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(10_000_001L));
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(111L));

	}

	@DisplayName("PercentDiscount 생성할 때 인자(discountAmount)허가되지 않은 값을 넣었을 때 실패 테스트")
	@Test
	void not_allowed_PercentVoucher_param() {
		VoucherType percentVoucherType = VoucherType.getVoucherType("2");
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(-10));
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(101));
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(300));

	}
}
