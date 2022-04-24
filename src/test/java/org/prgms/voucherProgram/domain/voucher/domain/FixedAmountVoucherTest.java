package org.prgms.voucherProgram.domain.voucher.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FixedAmountVoucherTest {

    @DisplayName("정해진 할인금액으로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,100,900", "500,300,200", "10000,5000,5000"})
    void discount_Amount_ReturnDiscountAmount(long beforeDiscount, long discountAmount, long result) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }

    @DisplayName("금액이 할인금액보다 이하라면 0을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,1000", "300,5000", "1000,5000"})
    void discount_BeforeDiscountIsUnderDiscountAmount_ReturnZero(long beforeDiscount, long discountAmount) {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        // when
        long discountPrice = voucher.discount(beforeDiscount);
        // then
        assertThat(discountPrice).isZero();
    }

}
