package com.programmers.voucher.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherEnumTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void decideVoucherType(int number) {
        VoucherEnum voucherEnum = VoucherEnum.decideVoucherType(number);
        switch (voucherEnum) {
            case FIXED -> assertThat(voucherEnum).isSameAs(VoucherEnum.FIXED);
            case PERCENT -> assertThat(voucherEnum).isSameAs(VoucherEnum.PERCENT);
        }
    }

    @Test
    void decideVoucherType_error() {
        assertThatThrownBy(
                () -> VoucherEnum.decideVoucherType(100)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
