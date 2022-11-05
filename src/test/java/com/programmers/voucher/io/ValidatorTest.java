package com.programmers.voucher.io;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.VoucherType;
import com.programmers.voucher.exception.OutOfPercentRangeException;
import com.programmers.voucher.exception.WrongDiscountTypeException;
import com.programmers.voucher.exception.WrongVoucherTypeException;

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

		Assertions.assertThatExceptionOfType(WrongVoucherTypeException.class)
			.isThrownBy(() -> {
				types.stream()
					.forEach(i -> validator.validateVoucherType(i));
			})
			.withMessageContaining("바우처 이름을 잘못 입력하셨습니다.");
	}

	@Test
	@DisplayName("할인 정도가 잘못된 타입으로 들어오면 예외가 발생한다.")
	void validateDiscountIsNumber() {
		List<String> discounts = new ArrayList<>();
		discounts.add("   ");
		discounts.add("이십");
		discounts.add("twenty");
		discounts.add("20.");

		Assertions.assertThatExceptionOfType(WrongDiscountTypeException.class)
			.isThrownBy(() -> {
				discounts.stream()
					.forEach(i -> validator.validateDiscount(VoucherType.FIXED.getType(), i));
			})
			.withMessageContaining("할인값을 숫자로 입력해주세요");
	}

	@Test
	@DisplayName("Percent 바우처일 때, 할인 정도가 0~100 범위 밖이면 예외가 발생한다.")
	void validateDiscountPercentRange() {
		List<String> discounts = new ArrayList<>();
		discounts.add("120");
		discounts.add("-5");

		Assertions.assertThatExceptionOfType(OutOfPercentRangeException.class)
			.isThrownBy(() -> {
				discounts.stream()
					.forEach(i -> validator.validateDiscount(VoucherType.PERCENT.getType(), i));
			})
			.withMessageContaining("잘못된 할인 범위입니다.");
	}
}