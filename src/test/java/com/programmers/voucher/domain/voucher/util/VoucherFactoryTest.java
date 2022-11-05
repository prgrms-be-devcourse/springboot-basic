package com.programmers.voucher.domain.voucher.util;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

class VoucherFactoryTest {

	VoucherFactory factory = new VoucherFactory();

	@Test
	@DisplayName("원하는 타입으로 바우처 생성이 성공한다.")
	void makeVoucher() {
		String fixedType = VoucherType.FIXED.getType();
		String percentType = VoucherType.PERCENT.getType();
		int fixedAmount = 1000;
		int percentAmount = 10;

		Voucher fixedVoucher = factory.makeVoucher(fixedType, UUID.randomUUID(), fixedAmount);
		Voucher percentVoucher = factory.makeVoucher(percentType, UUID.randomUUID(), percentAmount);

		Assertions.assertThat(fixedVoucher).isInstanceOf(FixedDiscountVoucher.class);
		Assertions.assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
	}
}