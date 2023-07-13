package com.prgms.springbootbasic.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PercentAmountVoucherTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void PERCENT_바우처는_0이하_100초과면_예외(int percent) {
        // given
        UUID voucherId = UUID.randomUUID();
        assertThatThrownBy(() -> {
            new PercentAmountVoucher(voucherId, percent);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

}