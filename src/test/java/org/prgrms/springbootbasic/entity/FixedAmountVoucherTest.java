package org.prgrms.springbootbasic.entity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.exception.AmountRangeMaxException;
import org.prgrms.springbootbasic.exception.AmountRangeMinException;

class FixedAmountVoucherTest {

    @DisplayName("정상 케이스 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        int amount = 10;

        //when
        var voucher = new FixedAmountVoucher(voucherId, amount);

        //then
        Assertions.assertAll(
            () -> assertThat(voucher.getVoucherId()).isEqualTo(voucherId),
            () -> assertThat(voucher.getAmount()).isEqualTo(amount)
        );
    }

    @DisplayName("amount 값이 0보다 작거나 같은 경우 예외 테스트")
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void amountLessThanOrEqualZero(int amount) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), amount))
            .isInstanceOf(AmountRangeMinException.class);
    }

    @DisplayName("amount 최대값 초과")
    @Test
    void excessMaxRange() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), 100000))
            .isInstanceOf(AmountRangeMaxException.class);
    }
}
