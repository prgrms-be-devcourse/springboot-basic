package com.example.demo.domain.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VoucherTest {

    @DisplayName("<정액 할인 바우처> 할인 테스트")
    @ParameterizedTest
    @CsvSource({"1000,200,800", "2000,500,1500"})
    void 정액_바우처_할인_테스트(double itemPrice, double discountAmount, double expected) {
        Voucher voucher = new FixedAmountVoucher(discountAmount);

        Assertions.assertThat(voucher.discount(itemPrice)).isEqualTo(expected);
    }

    @DisplayName("<정율 할인 바우처> 할인 테스트")
    @ParameterizedTest
    @CsvSource({"1000,10,900", "2000,50,1000"})
    void 정율_바우처_할인_테스트(double itemPrice, double discountAmount, double expected) {
        Voucher voucher = new PercentDiscountVoucher(discountAmount);

        Assertions.assertThat(voucher.discount(itemPrice)).isEqualTo(expected);
    }

}