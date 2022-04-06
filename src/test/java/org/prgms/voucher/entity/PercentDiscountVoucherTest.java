package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgms.voucher.exception.WrongDiscountPercentException;

class PercentDiscountVoucherTest {

    @DisplayName("할인퍼센트가 1미만이거나 100초과이면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 140, 1000})
    void percentDiscount_PercentIsOverHundredOrLessOne_ThrowsException(long discountPercent) {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), discountPercent))
            .isInstanceOf(WrongDiscountPercentException.class)
            .hasMessage("[ERROR] 올바른 할인퍼센트가 아닙니다.");
    }

    @DisplayName("정해진 할인퍼센트로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,10,900", "2000,20,1600", "532,30,372"})
    void discount_Percent_ReturnDiscountPrice(long beforeDiscount, long discountPercent, long result) throws
        WrongDiscountPercentException {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }

}
