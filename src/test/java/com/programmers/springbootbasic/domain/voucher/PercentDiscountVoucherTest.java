package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {

    @Test
    void discount() {
        // given
        Long price = 4550L;
        int percent = 33;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(),
                percent + "% 할인권",
                LocalDateTime.MAX,
                percent);

        Long expectedPrice = Math.round(price * ((100 - percent) / 100d) / 10) * 10;

        // when
        Long discountedPrice = voucher.discount(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }

    @ParameterizedTest
    @CsvSource(value = {"4550,33,3050", "5500,0,5500"})
    void discount(Long price, int percent, Long expectedPrice) {
        // given
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(),
                percent + "% 할인권",
                LocalDateTime.MAX,
                percent);

        // when
        Long discountedPrice = voucher.discount(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}