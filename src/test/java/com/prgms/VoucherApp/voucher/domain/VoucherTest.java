package com.prgms.VoucherApp.voucher.domain;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @ParameterizedTest
    @CsvSource(value = {"1000:9000", "2000:8000", "0:10000", "500:9500", "10500:0"}, delimiter = ':')
    @DisplayName("고정 값 할인")
    void fixedAmountDiscount(BigDecimal fixedAmount, BigDecimal result) {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
        assertThat(fixedAmountVoucher.discount(BigDecimal.valueOf(10000L))).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"10:9000", "15:8500", "100:0", "0:10000", "50:5000"}, delimiter = ':')
    @DisplayName("비율 값 할인")
    void percentDiscount(BigDecimal percent, BigDecimal result) {
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        assertThat(percentDiscountVoucher.discount(BigDecimal.valueOf(10000L))).isEqualTo(result);
    }

    @Test
    @DisplayName("비율 값 할인 무한 소수 예외 테스트")
    void percentDiscountException() {
        try {
            percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(3.33));
        } catch (ArithmeticException e) {
            Assertions.assertThatExceptionOfType(ArithmeticException.class)
                    .isThrownBy(() -> percentDiscountVoucher.discount(BigDecimal.valueOf(1)));
        }
    }
}