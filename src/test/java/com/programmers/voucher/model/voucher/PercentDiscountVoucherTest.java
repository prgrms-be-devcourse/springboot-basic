package com.programmers.voucher.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PercentDiscountVoucherTest {
    @Test
    @DisplayName("할인퍼센트가 0인 경우 예외를 발생시킨다.")
    void zeroDiscountValue() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(),0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인퍼센트가 0보다 작은 경우 예외를 발생시킨다.")
    void underZeroDiscountValue() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(),-50))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인퍼센트가 100보다 큰 경우 예외를 발생시킨다.")
    void bigDiscountValue() {
        assertThatThrownBy(
                () -> new PercentDiscountVoucher(UUID.randomUUID(), 120))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인퍼센트만큼 할인 후의 값을 반환한다.")
    void discount() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        //when
        long result = voucher.discount(10000);

        //then
        assertThat(result).isEqualTo(5000);
    }
}
