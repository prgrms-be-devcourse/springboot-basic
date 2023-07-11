package com.prgms.VoucherApp.domain.voucher;

import com.prgms.VoucherApp.domain.voucher.model.PercentDiscountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {


    @ParameterizedTest
    @CsvSource(value = {"10:9000", "15:8500", "100:0", "0:10000", "50:5000", "33.3:6670.0"}, delimiter = ':')
    @DisplayName("비율 값 할인")
    void percentDiscount(BigDecimal percent, BigDecimal result) {
        // given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);

        // when
        BigDecimal actualDiscount = percentDiscountVoucher.discount(BigDecimal.valueOf(10000L));

        // then
        assertThat(actualDiscount).isEqualTo(result);
    }
}