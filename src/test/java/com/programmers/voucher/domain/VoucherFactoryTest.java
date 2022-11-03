package com.programmers.voucher.domain;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.AppConfig;
import com.programmers.voucher.exception.WrongVoucherTypeException;

class VoucherFactoryTest {

	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	VoucherFactory factory = applicationContext.getBean(VoucherFactory.class);

	@Test
	@DisplayName("원하는 타입으로 바우처 생성이 성공한다.")
	void makeVoucher() {
		String fixedType = "FixedDiscountVoucher";
		String percentType = "PercentDiscountVoucher";
		int fixedAmount = 1000;
		int percentAmount = 10;

		Voucher fixedVoucher = factory.makeVoucher(fixedType, UUID.randomUUID(), fixedAmount);
		Voucher percentVoucher = factory.makeVoucher(percentType, UUID.randomUUID(), percentAmount);

		Assertions.assertThat(fixedVoucher).isInstanceOf(FixedDiscountVoucher.class);
		Assertions.assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
	}

	@Test
	@DisplayName("잘못된 타입이 입력되면 바우처 생성이 실패한다.")
	void failMakeVoucher() {
		String wrongType = "fixedPercent";
		int wrongAmount = 1000;

		Assertions.assertThatExceptionOfType(WrongVoucherTypeException.class)
			.isThrownBy(() -> factory.makeVoucher(wrongType, UUID.randomUUID(), wrongAmount))
			.withMessageContaining("바우처 이름을 잘못 입력하셨습니다.");
	}
}