package devcourse.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource({
            "FIXED, 1000, 200, 800",
            "FIXED, 1000, 100, 900",
            "PERCENT, 1000, 20, 800",
            "PERCENT, 1000, 10, 900"
    })
    @DisplayName("할인 쿠폰을 적용하면 할인된 금액을 반환합니다.")
    void testApplyDiscount(VoucherType voucherType, long amount, long discountValue, long expectedDiscountedAmount) {
        // When
        long discountedAmount = voucherType.applyDiscount(amount, discountValue);

        // Then
        assertThat(discountedAmount).isEqualTo(expectedDiscountedAmount);
    }

    @ParameterizedTest
    @CsvSource({
            "FIXED, 100, true",
            "FIXED, 1000, true",
            "FIXED, 0, false",
            "FIXED, -50, false",
            "PERCENT, 50, true",
            "PERCENT, 100, true",
            "PERCENT, 0, false",
            "PERCENT, -10, false",
            "PERCENT, 110, false"
    })
    @DisplayName("할인 쿠폰의 할인 금액이 유효한지 검증합니다.")
    void testValidateDiscountValue(VoucherType voucherType, long discountValue, boolean expectedValidationResult) {
        // When
        boolean validationResult = voucherType.validateDiscountValue(discountValue);

        // Then
        assertThat(validationResult).isEqualTo(expectedValidationResult);
    }
}
