package org.prgrms.springbootbasic.entity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PercentDiscountVoucherTest {

    @DisplayName("정상 케이스 테스트")
    @Test
    void test() {
        //given
        UUID voucherId = UUID.randomUUID();
        int percent = 10;

        //when
        var voucher = new PercentDiscountVoucher(voucherId, percent);

        //then
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getPercent()).isEqualTo(percent);
    }

    @DisplayName("percent 0보다 작은 케이스 테스트")
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void percentLessOrEqualToZero(int percent) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), percent))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(PercentDiscountVoucher.PERCENT_MIN_RANGE_EXP_MSG);
    }

    @DisplayName("percent 100보다 큰 케이스")
    @Test
    void percentExcessMaxRange() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 101))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(PercentDiscountVoucher.PERCENT_MAX_RANGE_EXP_MSG);
    }

}