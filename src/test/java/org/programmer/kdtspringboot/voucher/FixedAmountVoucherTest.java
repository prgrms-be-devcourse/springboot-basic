package org.programmer.kdtspringboot.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class FixedAmountVoucherTest {

    @Test
    @DisplayName("할인을 해주는 discount 성공테스트")
    void DiscountFixSuccessesTest() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(),100L);
        assertThat(100L).isEqualTo(sut.discount(200L));
    }

    @Test
    @DisplayName("할인량이 음수면 안됨")
    void DiscountFixNotMinusTest() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), -100L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인량이 10000넘으면 안됨")
    void DiscountFixNotOver10000Test() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), 10001L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인이 최대로 된값이 0인 테스트")
    void DiscountResultZeroTest() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(),100L);
        assertThat(0L).isEqualTo(sut.discount(50L));
    }

}