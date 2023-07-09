package co.programmers.voucher_management.voucher.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.programmers.voucher_management.exception.InvalidVoucherAmountException;

class FixedDiscountTest {
	@Nested
	@DisplayName("amount의 범위가")
	class range_of_amount {
		@ParameterizedTest
		@ValueSource(ints = {
				0,
				-500,
				5000000
		})
		@DisplayName("유효하지 않은 경우 Exception을 던진다 : 0 이하이거나 1,000,000 이상인 경우")
		void is_invalid(int amount) {
			assertThrows(InvalidVoucherAmountException.class, () -> new FixedDiscount(amount));
		}

		@ParameterizedTest
		@ValueSource(ints = {
				5000,
				10000,
				250000,
				990000
		})
		@DisplayName("유효한 경우 객체를 생성한다 : 0 과 1,000,000 사이인 경우")
		void is_valid(int amount) {
			FixedDiscount fixedDiscount = new FixedDiscount(amount);
			assertThat(amount, is(equalTo(fixedDiscount.getAmount())));
			assertThat(fixedDiscount.getType(), is(equalTo("fixed")));
		}

	}

	@Nested
	@DisplayName("기존 금액을 파라미터로 전달하면")
	class discount {
		@ParameterizedTest
		@CsvSource(value = {
				"10000-5000=5000",
				"150000-50000=100000",
				"55000000-990000=54010000"
		},
				delimiter = '='
		)
		@DisplayName("할인 금액이 기존 금액보다 작으면 할인된 금액을 반환한다")
		void amount_is_smaller(String process, int finalPrice) {
			String[] parsedData = process.split("-");
			int originalPrice = Integer.parseInt(parsedData[0]);
			int discountAmount = Integer.parseInt(parsedData[1]);
			FixedDiscount fixedDiscount = new FixedDiscount(discountAmount);
			assertThat(fixedDiscount.discount(originalPrice), is(equalTo(finalPrice)));
		}

		@ParameterizedTest
		@CsvSource(value = {
				"10000-50000",
				"150000-550000",
				"490000-990000"
		},
				delimiter = '-'
		)
		@DisplayName("할인 금액이 기존 금액보다 큰 경우 0원을 반환한다")
		void amount_is_bigger(Integer originalPrice, Integer discountAmount) {
			FixedDiscount fixedDiscount = new FixedDiscount(discountAmount);
			assertThat(fixedDiscount.discount(originalPrice), is(equalTo(0)));
		}
	}

}
