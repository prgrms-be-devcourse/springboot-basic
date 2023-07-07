package com.prgrms.springbootbasic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.prgrms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("비율 할인 바우처 생성 테스트")
    void createPercentDiscountVoucher() {
        // given
        long discount = 20;

        // when
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(discount);

        // then
        assertEquals(discount, percentDiscountVoucher.getDiscount());
    }

    @ParameterizedTest
    @CsvSource({"10", "30", "50", "70"})
    @DisplayName("생성된 여러 개의 할인 바우처 확인 테스트")
    void createdPercentDiscountVoucherEquals(long discount) {
        // given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(discount);

        // when

        // then
        assertEquals(discount, percentDiscountVoucher.getDiscount());
    }
}
