package com.programmers.springweekly.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VoucherTest {

    @Test
    @DisplayName("바우처 팩토리를 통해 고정 할인 객체를 생성 할 수 있다.")
    void createFixedDiscountOfFactory() {
        // given && when
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.FIXED, 1000L);

        // then
        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"accVoucher", "divideVoucher"})
    @DisplayName("바우처 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void voucherTypeTest(String input) {
        assertThatThrownBy(() -> VoucherType.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 바우처 타입이 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1000:10000:9000", "2000:3000:1000", "10000:20000:10000"}, delimiter = ':')
    @DisplayName("고정된 값을 입력하여 고정 할인을 진행할 수 있다.")
    void proceedFixedDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("바우처 팩토리를 통해 퍼센트 할인 객체를 생성 할 수 있다.")
    void createPercentDiscountOfFactory() {
        // given && when
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.PERCENT, 100L);

        // then
        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"30:9000:6300", "10:10000:9000", "50:20000:10000"}, delimiter = ':')
    @DisplayName("0~100퍼센트 사이의 값을 받아 퍼센트 할인을 진행 할 수 있다.")
    void proceedPercentDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

}
