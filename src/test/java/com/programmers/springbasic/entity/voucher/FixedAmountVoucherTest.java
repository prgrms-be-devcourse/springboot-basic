package com.programmers.springbasic.entity.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

	@Test
	void 유효한_금액_주어졌을때_정액_할인권_성공적으로_생성한다() {
		UUID voucherId = UUID.randomUUID();
		long amount = 100;

		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

		assertEquals(voucherId, voucher.getVoucherId());
		assertEquals(amount, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_금액_주어졌을때_정액_할인권_생성시_예외발생한다() {
		UUID voucherId = UUID.randomUUID();
		long invalidAmount = 0;

		assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(voucherId, invalidAmount));
	}

	@Test
	void 유효한_금액_주어졌을때_할인_값_변경_성공한다() {
		UUID voucherId = UUID.randomUUID();
		long initialAmount = 100;
		long newAmount = 150;
		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, initialAmount);

		voucher.changeDiscountValue(newAmount);

		assertEquals(newAmount, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_금액_주어졌을때_할인_값_변경시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long initialAmount = 100;
		long invalidAmount = -50;
		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, initialAmount);

		assertThrows(IllegalArgumentException.class, () -> voucher.changeDiscountValue(invalidAmount));
	}
}
