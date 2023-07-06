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

class PercentageDiscountTest {
	@Nested
	@DisplayName("amount의 범위가")
	class range_of_amount {
		@ParameterizedTest
		@ValueSource(ints = {
				200,
				-50,
				0,
				101
		})
		@DisplayName("유효하지 않은 경우 Exception을 던진다 : 0 보다 작거나 같고 100보다 큰 경우")
		void is_invalid(int amount) {
			assertThrows(InvalidVoucherAmountException.class, () -> new PercentageDiscount(amount));
		}

		@ParameterizedTest
		@ValueSource(ints = {
				50,
				100,
				45,
				33
		})
		@DisplayName("유효한 경우 객체를 생성한다 : 0 보다 크고 100보다 작거나 같은 경우")
		void is_valid(int amount) {
			PercentageDiscount percentageDiscount = new PercentageDiscount(amount);
			assertThat(amount, equalTo(percentageDiscount.getAmount()));
			assertThat(percentageDiscount.getType(), equalTo("percent"));
		}

	}

	@Nested
	@DisplayName("기존 금액을 파라미터로 전달하면")
	class discount {
		@ParameterizedTest
		@CsvSource(value = {
				"10000,50:5000",
				"150000,30:105000",
				"55000000,10:49500000"
		},
				delimiter = ':'
		)
		@DisplayName("할인된 금액을 반환한다")
		void discount(String process, int finalPrice) {
			String[] parsedData = process.split(",");
			int originalPrice = Integer.parseInt(parsedData[0]);
			int discountAmount = Integer.parseInt(parsedData[1]);
			PercentageDiscount percentageDiscount = new PercentageDiscount(discountAmount);
			assertThat(percentageDiscount.discount(originalPrice), equalTo(finalPrice));
		}
	}

}
