package org.prgms.springbootbasic.voucher.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

	@DisplayName("FixedAmountVoucher 생성 테스트")
	@Test
	void FixedAmountVoucher_create_test() {
		// 생성 성공 테스트
		Voucher voucher = new FixedAmountVoucher(2000L);
		assertNotNull(voucher.getVoucherId());

		// 생성 실패 테스트
		// Null은 생성자가 하나이므로 컴파일 타임에 체크?
		// 할인 금액이 음수이면 안됨
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-100L));
		// 할인 금액의 최소 단위는 10원 이어야 함
		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-1L));

	}

	@DisplayName("discount 통과 테스트. voucher의 할인 금액만큼 할인되어야 한다.")
	@Test
	void discount_pass_test() {
		Voucher voucher = new FixedAmountVoucher(1000L);

		assertEquals(19000,voucher.discount(20000));
		assertEquals(10000,voucher.discount(11000));
	}

	@DisplayName("discount 실패 테스트")
	@Test
	void discount_fail_test() {
		Voucher voucher = new FixedAmountVoucher(2000L);

		// beforeDiscount의 금액은 음수가 아니어야 한다.
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-10L));
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-100L));

	}


}
