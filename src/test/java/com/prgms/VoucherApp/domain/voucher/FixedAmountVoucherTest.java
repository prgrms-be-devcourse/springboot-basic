package com.prgms.VoucherApp.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @CsvSource(value = {"1000:9000", "2000:8000", "0:10000", "500:9500", "10500:0"}, delimiter = ':')
    @DisplayName("고정 비용으로 할인이 된다.")
    void fixedAmountDiscount(BigDecimal fixedAmount, BigDecimal result) {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount, VoucherType.FIXED_VOUCHER);
        assertThat(fixedAmountVoucher.discount(BigDecimal.valueOf(10000L))).isEqualTo(result);
    }
}