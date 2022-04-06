package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgms.voucher.exception.ZeroDiscountPercentException;

class PercentDiscountVoucherTest {

    @DisplayName("할인퍼센트가 0이면 예외를 발생한다.")
    @Test
    void percentDiscount_PercentIsZero_ThrowsException() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 0))
            .isInstanceOf(ZeroDiscountPercentException.class)
            .hasMessage("[ERROR] 할인퍼센트가 0입니다.");
    }

    @DisplayName("정해진 할인퍼센트로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,10,900", "2000,20,1600", "532,30,372"})
    void discount_Percent_ReturnDiscountPrice(long beforeDiscount, long discountPercent, long result) throws
        ZeroDiscountPercentException {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }

}
