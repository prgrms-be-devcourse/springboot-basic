package org.prgms.voucherProgram.domain.voucher.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PercentDiscountVoucherTest {
    @DisplayName("정해진 할인퍼센트로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,10,900", "2000,20,1600", "532,30,372", "1000,100,0"})
    void discount_Percent_ReturnDiscountPrice(long beforeDiscount, long discountPercent, long result) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }
}
