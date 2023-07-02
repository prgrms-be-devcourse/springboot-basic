package com.programmers.springweekly.domain.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VoucherTest {

    @ParameterizedTest
    @CsvSource(value = {"1000:10000:9000", "2000:3000:1000", "10000:20000:10000"}, delimiter = ':')
    @DisplayName("고정 할인을 진행한다.")
    void proceedFixedDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new FixedAmountVoucher(discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        Assertions.assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest
    @CsvSource(value = {"30:9000:6000", "10:10000:9000", "50:20000:10000"}, delimiter = ':')
    @DisplayName("퍼센트 할인을 진행한다.")
    void proceedPercentDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new PercentDiscountVoucher(discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        Assertions.assertThat(discountPrice).isEqualTo(expectedDiscount);
    }
}
