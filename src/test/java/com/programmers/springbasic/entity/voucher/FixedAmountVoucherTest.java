package com.programmers.springbasic.entity.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

	@Test
	void 유효한_금액_주어졌을때_정액_할인권_성공적으로_생성한다() {
		UUID voucherId = UUID.randomUUID();
		long amount = 100;

		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount, LocalDateTime.now());

		assertEquals(voucherId, voucher.getVoucherId());
		assertEquals(amount, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_금액_주어졌을때_정액_할인권_생성시_예외발생한다() {
		UUID voucherId = UUID.randomUUID();
		long invalidAmount = 0;

		assertThrows(IllegalArgumentException.class,
			() -> new FixedAmountVoucher(voucherId, invalidAmount, LocalDateTime.now()));
	}

	@Test
	void 유효한_금액_주어졌을때_할인_값_변경_성공한다() {
		UUID voucherId = UUID.randomUUID();
		long initialAmount = 100;
		long newAmount = 150;
		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, initialAmount, LocalDateTime.now());

		voucher.changeDiscountValue(newAmount);

		assertEquals(newAmount, voucher.getDiscountValue());
	}

	@Test
	void 잘못된_금액_주어졌을때_할인_값_변경시_예외_발생한다() {
		UUID voucherId = UUID.randomUUID();
		long initialAmount = 100;
		long invalidAmount = -50;
		FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, initialAmount, LocalDateTime.now());

		assertThrows(IllegalArgumentException.class, () -> voucher.changeDiscountValue(invalidAmount));
	}
}
