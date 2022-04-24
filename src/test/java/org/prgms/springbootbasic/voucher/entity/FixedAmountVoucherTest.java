package org.prgms.springbootbasic.voucher.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

	@DisplayName("FixedAmountVoucher 생성 성공 테스트")
	@Test
	void FixedAmountVoucher_create_test() {
		// 생성 성공 테스트
		Voucher voucher = new FixedAmountVoucher(2000);
		assertNotNull(voucher.getVoucherId());

		Voucher voucher1 = new FixedAmountVoucher(2000);
		assertNotNull(voucher1.getVoucherId());

	}

	@DisplayName("discountAmount(할인금액)은 0원에서 1000만원의 범위를 가진다..")
	@Test
	void discountAmount_must_be_between_0_10000000() {
		// 음수 체크
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-100));
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-1000000));

		// 1000만원 이상 체크
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(10_000_001));
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(20_000_000));
	}

	@DisplayName("discountAmount(할인금액)의 최소 단위는 100원이다")
	@Test
	void name() {
		assertDoesNotThrow(() -> new FixedAmountVoucher(100));
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(11));
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(3050));

	}

	@DisplayName("discount 통과 테스트. voucher의 할인 금액만큼 할인되어야 한다.")
	@Test
	void discount_pass_test() {
		Voucher voucher = new FixedAmountVoucher(1000);

		assertEquals(19000,voucher.discount(20000));
		assertEquals(10000,voucher.discount(11000));
	}

	@DisplayName("discount 실패 테스트")
	@Test
	void discount_fail_test() {
		Voucher voucher = new FixedAmountVoucher(2000);

		// beforeDiscount의 금액은 음수가 아니어야 한다.
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-10));
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-100));

	}


}
