package org.prgms.springbootbasic.voucher.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

	@DisplayName("PercentDiscountVoucher 생성 테스트")
	@Test
	void PercentDiscountVoucher_create_test() {
		Voucher voucher = new PercentDiscountVoucher(50);
		assertNotNull(voucher.getVoucherId());
	}

	@DisplayName("PercentDiscountVoucher 생성 실패 테스트")
	@Test
	void PercentVoucher_create_fail_test() {
		// discountPercent(할인율)은 0 ~ 100 사이의 정수여야 한다.
		Voucher voucher = new PercentDiscountVoucher(25);
		assertNotNull(voucher.getVoucherId());

		assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(-1));
		assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(101));
	}

	@DisplayName("discount 성공 테스트. discount 메서드는 할인된 금액을 제외한 금액을 반환해야 한다.")
	@Test
	void discount_pass_test() {
		Voucher voucher = new PercentDiscountVoucher(10);
		assertEquals(90L, voucher.discount(100));
		assertEquals(180L, voucher.discount(200));
	}

	@DisplayName("discount 실패 테스트")
	@Test
	void discount_fail_test() {
		Voucher voucher = new PercentDiscountVoucher(20);

		// beforeAmount는 음수이면 안된다.
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-20));
		assertThrows(IllegalArgumentException.class, () -> voucher.discount(-1));
	}
}
