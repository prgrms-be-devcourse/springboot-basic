package co.programmers.voucher_management.voucher.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;

class DiscountTypeGeneratorTest {
	@Nested
	@DisplayName("입력된 할인 타입이")
	class discountType {
		@ParameterizedTest
		@CsvSource(value = {
				"fixed, 10000",
				"percent, 50"
		}, delimiter = ',')
		@DisplayName("유효한 경우 객체를 반환한다")
		void is_valid(String discountType, int discountAmount) {
			DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
			assertThat(discountStrategy.getType(), is(equalTo(discountType)));
			assertThat(discountStrategy.getAmount(), is(equalTo(discountAmount)));
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"invalidType",
				"Error",
				"discount type"})
		@DisplayName("유효하지 않은 경우 NoSuchTypeException을 던진다")
		void is_invalid(String discountType) {
			int discountAmount = 50;
			assertThrows(InvalidDataException.class, () -> DiscountTypeGenerator.of(discountType, discountAmount));
		}
	}

}
