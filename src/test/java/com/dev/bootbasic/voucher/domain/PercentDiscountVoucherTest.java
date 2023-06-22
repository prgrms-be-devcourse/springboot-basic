package com.dev.bootbasic.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.PercentDiscountVoucher.PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {

    @DisplayName("백분율 할인 바우처의 할인금액 성공 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 50, 100})
    void validateDiscountAmountSuccessTest(int discountAmount) {
        UUID id = UUID.randomUUID();

        AbstractVoucher voucher = PercentDiscountVoucher.of(id, discountAmount);

        assertThat(voucher)
                .extracting(AbstractVoucher::getId, AbstractVoucher::getDiscountAmount)
                .containsExactly(id, discountAmount);
    }

    @DisplayName("백분율 할인 바우처의 할인금액 예외 테스트")
    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void validateDiscountAmountFailTest(int discountAmount) {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> PercentDiscountVoucher.of(id, discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
    }

    @DisplayName("백분율 할인 후 금액 테스트")
    @ParameterizedTest
    @CsvSource({
            "10, 10000, 9000",
            "20, 50000, 40000",
            "100, 8000, 0"
    })
    void discountTest(int discountAmount, int price, int expected) {
        UUID id = UUID.randomUUID();
        Voucher voucher = PercentDiscountVoucher.of(id, discountAmount);

        assertThat(voucher.discount(price)).isEqualTo(expected);
    }

}
