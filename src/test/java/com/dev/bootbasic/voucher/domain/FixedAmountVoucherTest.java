package com.dev.bootbasic.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.FixedAmountVoucher.FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {


    @DisplayName("고정금액 할인 바우처의 할인금액 성공 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1000, 50000, 100000})
    void validateDiscountAmountSuccessTest(int discountAmount) {
        UUID id = UUID.randomUUID();

        AbstractVoucher voucher = FixedAmountVoucher.of(id, discountAmount);

        assertThat(voucher)
                .extracting(AbstractVoucher::getId, AbstractVoucher::getDiscountAmount)
                .containsExactly(id, discountAmount);
    }

    @DisplayName("고정금액 할인 바우처의 할인금액 예외 테스트")
    @ParameterizedTest
    @ValueSource(ints = {999, 100001})
    void validateDiscountAmountFailTest(int discountAmount) {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> FixedAmountVoucher.of(id, discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
    }

    @DisplayName("고정금액 할인 후 금액 테스트")
    @ParameterizedTest
    @CsvSource({
            "6000, 10000, 4000",
            "5000, 15000, 10000",
            "3000, 8000, 5000"
    })
    void discountTest(int discountAmount, int price, int expected) {
        UUID id = UUID.randomUUID();
        Voucher voucher = FixedAmountVoucher.of(id, discountAmount);

        assertThat(voucher.discount(price)).isEqualTo(expected);
    }

    @DisplayName("상품 가격보다 바우처 할인 금액이 큰 경우 0원을 반환한다.")
    @Test
    void discountZeroPayTest(){
        UUID id = UUID.randomUUID();
        Voucher voucher = FixedAmountVoucher.of(id, 5000);

        assertThat(voucher.discount(4000)).isEqualTo(0);
     }

}
