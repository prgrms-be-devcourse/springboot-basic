package com.programmers.voucher.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FixedAmountVoucherTest {

    private final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);

    @Test
    @DisplayName("할인금액이 0인 경우 예외를 발생시킨다.")
    void zeroDiscountValue() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(),0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인금액이 0보다 작은 경우 예외를 발생시킨다.")
    void underZeroDiscountValue() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(),-1000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인금액이 정가보다 큰 경우 예외를 발생시킨다.")
    void bigDiscountValue() {
        assertThatThrownBy(
                () -> voucher.discount(2000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인금액만큼 할인 후의 값을 반환한다.")
    void discount() {
        //when
        long result = voucher.discount(15000);

        //then
        assertThat(result).isEqualTo(10000);
    }
}
