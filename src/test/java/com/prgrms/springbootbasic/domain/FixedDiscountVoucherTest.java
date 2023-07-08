package com.prgrms.springbootbasic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.springbootbasic.domain.voucher.FixedDiscountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FixedDiscountVoucherTest {

    @Test
    @DisplayName("고정 할인 바우처 생성 테스트")
    void createFixedVoucherTest() {
        // given
        long discount = 10000;

        // when
        FixedDiscountVoucher fixedDiscountVoucher = new FixedDiscountVoucher(discount);

        // then
        assertThat(fixedDiscountVoucher.getDiscount()).isEqualTo(discount);
    }

    @ParameterizedTest
    @CsvSource({"1000, 10000", "200000, 5000"})
    @DisplayName("여러 개 고정 할인 바우처 확인 테스트")
    void createdFixDiscountVoucherEquals(long discount1, long discount2) {
        // given
        FixedDiscountVoucher fixedDiscountVoucher1 = new FixedDiscountVoucher(discount1);
        FixedDiscountVoucher fixedDiscountVoucher2 = new FixedDiscountVoucher(discount2);

        // when

        // then
        assertThat(fixedDiscountVoucher1.getDiscount()).isEqualTo(discount1);
        assertThat(fixedDiscountVoucher2.getDiscount()).isEqualTo(discount2);
    }
}
