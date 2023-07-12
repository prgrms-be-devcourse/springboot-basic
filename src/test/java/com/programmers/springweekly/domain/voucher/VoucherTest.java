package com.programmers.springweekly.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VoucherTest {

    @Test
    @DisplayName("팩토리에서 고정 할인 클래스를 생성 할 수 있다.")
    void createFixedDiscountOfFactory() {
        // given && when
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.FIXED, 1000L);

        // then
        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
    }


    @ParameterizedTest
    @CsvSource(value = {"1000:10000:9000", "2000:3000:1000", "10000:20000:10000"}, delimiter = ':')
    @DisplayName("고정 할인을 진행할 수 있다.")
    void proceedFixedDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("팩토리에서 퍼센트 할인 클래스를 생성 할 수 있다.")
    void createPercentDiscountOfFactory() {
        // given && when
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.PERCENT, 100L);

        // then
        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"30:9000:6300", "10:10000:9000", "50:20000:10000"}, delimiter = ':')
    @DisplayName("퍼센트 할인을 진행 할 수 있다.")
    void proceedPercentDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }
    
}
