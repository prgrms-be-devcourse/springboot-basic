package com.programmers.voucher.domain.voucher.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.exception.ExceptionMessage;

class VoucherFactoryTest {

	VoucherFactory factory = new VoucherFactory();

	@Test
	@DisplayName("원하는 타입으로 바우처 생성이 성공한다.")
	void makeVoucher() {
		VoucherType fixedType = VoucherType.FIXED;
		VoucherType percentType = VoucherType.PERCENT;
		String fixedAmount = "1000";
		String percentAmount = "10";

		Voucher fixedVoucher = factory.makeVoucher(fixedType, UUID.randomUUID(), fixedAmount);
		Voucher percentVoucher = factory.makeVoucher(percentType, UUID.randomUUID(), percentAmount);

		Assertions.assertThat(fixedVoucher).isInstanceOf(FixedDiscountVoucher.class);
		Assertions.assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
	}

	@Test
	@DisplayName("할인값이 숫자가 아니면 예외가 발생한다.")
	void validateDiscountIsNumber() {
		List<String> discounts = new ArrayList<>();
		discounts.add("   ");
		discounts.add("이십");
		discounts.add("twenty");
		discounts.add("20.");

		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> {
				discounts.stream()
					.forEach(i -> factory.makeVoucher(VoucherType.FIXED, UUID.randomUUID(), i));
			})
			.withMessageContaining(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
	}

	@Test
	@DisplayName("Percent 바우처일 때, 할인 정도가 0~100 범위 밖이면 예외가 발생한다.")
	void validateDiscountPercentRange() {
		List<String> discounts = new ArrayList<>();
		discounts.add("120");
		discounts.add("-5");

		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> {
				discounts.stream()
					.forEach(i -> factory.makeVoucher(VoucherType.PERCENT, UUID.randomUUID(), i));
			})
			.withMessageContaining(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
	}
}