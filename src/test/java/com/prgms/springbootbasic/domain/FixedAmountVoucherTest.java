package com.prgms.springbootbasic.domain;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void FIXED_바우처는_0이나_음수값이_들어오면_예외를_던진다(int amount) {
        // given
        UUID voucherId = UUID.randomUUID();

        assertThatThrownBy(() -> {
            new FixedAmountVoucher(voucherId, amount);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

    }

}