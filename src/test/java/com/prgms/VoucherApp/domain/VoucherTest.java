package com.prgms.VoucherApp.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @ParameterizedTest
    @CsvSource(value = {"1000:9000", "2000:8000", "0:10000", "500:9500", "10500:0"}, delimiter = ':')
    @DisplayName("고정 값 할인")
    void fixedAmountDiscount(Long fixedAmount, Long result) {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
        assertThat(fixedAmountVoucher.discount(10000L)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"10:9000", "15:8500", "100:0", "0:10000", "50:5000"}, delimiter = ':')
    @DisplayName("비율 값 할인")
    void percentDiscount(Long percent, Long result) {
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        assertThat(percentDiscountVoucher.discount(10000L)).isEqualTo(result);
    }
}