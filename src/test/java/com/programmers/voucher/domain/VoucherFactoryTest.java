package com.programmers.voucher.domain;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.AppConfig;
import com.programmers.voucher.domain.voucher.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.domain.voucher.VoucherType;

class VoucherFactoryTest {

	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	VoucherFactory factory = applicationContext.getBean(VoucherFactory.class);

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