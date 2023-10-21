package study.dev.spring.voucher.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.dev.spring.voucher.exception.VoucherErrorCode.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import study.dev.spring.voucher.exception.VoucherException;

@DisplayName("[Voucher Test] - Domain Layer")
class VoucherTest {

	@Nested
	@DisplayName("[졍액할인 바우처를 생성한다]")
	class createFixedVoucherTest {

		@Test
		@DisplayName("[정액할인 바우처를 성공적으로 생성한다]")
		void success() {
			//given
			final String name = "name";
			final double discountAmount = 1000;

			//when
			Voucher actual = Voucher.of(VoucherType.FIXED, name, discountAmount);

			//then
			assertAll(
				() -> assertThat(actual.getName()).isEqualTo(name),
				() -> assertThat(actual.getTypeDescription()).isEqualTo(VoucherType.FIXED.getDescription()),
				() -> assertThat(actual.getDiscountAmount()).isEqualTo(discountAmount)
			);
		}

		@Test
		@DisplayName("[음수의 할인가로 실패한다]")
		void fail() {
			//given
			final String name = "name";
			final double discountAmount = -1000;

			//when
			ThrowingCallable when = () -> Voucher.of(VoucherType.FIXED, name, discountAmount);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(VoucherException.class)
				.hasMessageContaining(NEGATIVE_DISCOUNT_AMOUNT.getMessage());
		}
	}

	@Nested
	@DisplayName("[졍률할인 바우처를 생성한다]")
	class createPercentVoucherTest {

		@Test
		@DisplayName("[정률할인 바우처를 성공적으로 생성한다]")
		void success() {
			//given
			final String name = "name";
			final double discountAmount = 10;

			//when
			Voucher actual = Voucher.of(VoucherType.PERCENT, name, discountAmount);

			//then
			assertAll(
				() -> assertThat(actual.getName()).isEqualTo(name),
				() -> assertThat(actual.getTypeDescription()).isEqualTo(VoucherType.PERCENT.getDescription()),
				() -> assertThat(actual.getDiscountAmount()).isEqualTo(discountAmount)
			);
		}

		@Test
		@DisplayName("[0~100 범위외의 할인율로 실패한다]")
		void fail() {
			//given
			final String name = "name";
			final double discountAmount = -1000;

			//when
			ThrowingCallable when = () -> Voucher.of(VoucherType.PERCENT, name, discountAmount);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(VoucherException.class)
				.hasMessageContaining(INVALID_RANGE_DISCOUNT_AMOUNT.getMessage());
		}
	}
}
