package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @CsvSource(value = {"10000,5000,5000", "300,50000,0"})
    void discount(Long price, Long amount, Long expectedPrice) {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(),
                amount + "원 할인",
                LocalDateTime.MAX,
                amount);

        // when
        Long discountedPrice = voucher.discount(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}