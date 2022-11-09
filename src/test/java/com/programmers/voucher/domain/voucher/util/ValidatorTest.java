package com.programmers.voucher.domain.voucher.util;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.exception.ExceptionMessage;

class ValidatorTest {

	Validator validator = new Validator();

	@Test
	@DisplayName("잘못된 바우처 타입이 입력되면 예외가 발생한다.")
	void validateVoucherType() {
		List<String> types = new ArrayList<>();
		types.add("fixed");
		types.add("");
		types.add("   ");
		types.add("1234");

		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> {
				types.stream()
					.forEach(i -> validator.validateVoucherType(i));
			})
			.withMessageContaining(ExceptionMessage.WRONG_VOUCHER_TYPE.getMessage());
	}

	@Test
	@DisplayName("할인 정도가 잘못된 타입으로 들어오면 예외가 발생한다.")
	void validateDiscountIsNumber() {
		List<String> discounts = new ArrayList<>();
		discounts.add("   ");
		discounts.add("이십");
		discounts.add("twenty");
		discounts.add("20.");

		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> {
				discounts.stream()
					.forEach(i -> validator.validateDiscount(VoucherType.FIXED.getType(), i));
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
					.forEach(i -> validator.validateDiscount(VoucherType.PERCENT.getType(), i));
			})
			.withMessageContaining(ExceptionMessage.OUT_OF_PERCENT_RANGE.getMessage());
	}
}