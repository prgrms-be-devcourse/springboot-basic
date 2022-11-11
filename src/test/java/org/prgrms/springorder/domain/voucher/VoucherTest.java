package org.prgrms.springorder.domain.voucher;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTest {

	@Test
	@DisplayName("FixedAmount 바우처의 값이 0이상이면 객체 생성에 성공한다.")
	void test1() {
		Assertions.assertDoesNotThrow(() -> new FixedAmountVoucher(UUID.randomUUID(), 50));
	}

	@Test
	@DisplayName("FixedAmount 바우처의 값이 0 이하이면 객체 생성에 실패한다.")
	void test2() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new FixedAmountVoucher(UUID.randomUUID(), -100));
	}

	@Test
	@DisplayName("PercentAmount 바우처의 값이 0이상, 100이하이면 객체 생성에 성공한다.")
	void test3() {
		Assertions.assertDoesNotThrow(() -> new PercentDiscountVoucher(UUID.randomUUID(), 60));
	}

	@Test
	@DisplayName("PercentAmount 바우처의 값이 0미만 100초과이면 객체 생성에 실패한다.")
	void test4() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new PercentDiscountVoucher(UUID.randomUUID(), -50));
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new PercentDiscountVoucher(UUID.randomUUID(), 150));
	}

}