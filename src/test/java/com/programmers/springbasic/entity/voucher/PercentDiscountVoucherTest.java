package com.programmers.springbasic.entity.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

	@Test
	void 유효한_퍼센트_주어졌을때_비율_할인권_성공적으로_생성한다() {
		UUID voucherId = UUID.randomUUID();
		long percent = 50;

		PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

		assertEquals(voucherId, voucher.getVoucherId());
		assertEquals(percent, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_퍼센트_주어졌을때_비율_할인권_생성시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long invalidPercent = -10;

		assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherId, invalidPercent));
	}

	@Test
	void 최대값_초과_퍼센트_주어졌을때_비율_할인권_생성시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long invalidPercent = 110;

		assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherId, invalidPercent));
	}

	@Test
	void 유효한_퍼센트_주어졌을때_할인_값_변경_성공한다() {
		UUID voucherId = UUID.randomUUID();
		long initialPercent = 20;
		long newPercent = 30;
		PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, initialPercent);

		voucher.changeDiscountValue(newPercent);

		assertEquals(newPercent, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_퍼센트_주어졌을때_할인_값_변경시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long initialPercent = 20;
		long invalidPercent = -10;
		PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, initialPercent);

		assertThrows(IllegalArgumentException.class, () -> voucher.changeDiscountValue(invalidPercent));
	}

	@Test
	void 최대값_초과_퍼센트_주어졌을때_할인_값_변경시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long initialPercent = 20;
		long invalidPercent = 150;
		PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, initialPercent);

		assertThrows(IllegalArgumentException.class, () -> voucher.changeDiscountValue(invalidPercent));
	}
}
