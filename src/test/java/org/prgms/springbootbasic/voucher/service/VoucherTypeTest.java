package org.prgms.springbootbasic.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.prgms.springbootbasic.voucher.vo.VoucherType;

class VoucherTypeTest {

	@DisplayName("type에 따라 Voucher 생성 성공 테스트")
	@Test
	void createVoucher_pass_test() {
		//given
		VoucherType type = VoucherType.of(VoucherType.FIXEDAMOUNTVOUCHER.name());
		//when
		Voucher voucher = type.createVoucher(1000);
		//then
		assertEquals(VoucherType.FIXEDAMOUNTVOUCHER, voucher.getType());

		VoucherType type2 = VoucherType.of(VoucherType.PERCENTDISCOUNTVOUCHER.name());
		Voucher voucher2 = type2.createVoucher(50);

		assertEquals(VoucherType.PERCENTDISCOUNTVOUCHER, voucher2.getType());
	}

	@DisplayName("정해진 Button(1,2) 값이 아닌 다른 값을 입력했을 때 IllegalArgumentException 발생")
	@Test
	void not_allowed_button_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.of("3"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.of("a"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.of(""));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.of(null));

	}

	@DisplayName("FixedAMountvoucher를 생성할 때 인자(discountAmount)허가되지 않은 값을 넣었을 때 실패 테스트")
	@Test
	void not_allowed_FixedVoucher_param() {
		VoucherType fixedVoucherType = VoucherType.of(VoucherType.FIXEDAMOUNTVOUCHER.name());
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(-10));
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(10_000_001));
		assertThrows(IllegalArgumentException.class, () -> fixedVoucherType.createVoucher(111));

	}

	@DisplayName("PercentDiscount 생성할 때 인자(discountAmount)허가되지 않은 값을 넣었을 때 실패 테스트")
	@Test
	void not_allowed_PercentVoucher_param() {
		VoucherType percentVoucherType = VoucherType.of(VoucherType.PERCENTDISCOUNTVOUCHER.name());
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(-10));
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(101));
		assertThrows(IllegalArgumentException.class, () -> percentVoucherType.createVoucher(300));

	}
}
