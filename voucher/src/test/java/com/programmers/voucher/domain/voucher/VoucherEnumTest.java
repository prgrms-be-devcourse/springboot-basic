package com.programmers.voucher.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherEnumTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void decideVoucherType(int number) {
        VoucherEnum voucherEnum = VoucherEnum.decideVoucherType(number).get();
        switch (voucherEnum) {
            case FIXED -> assertThat(voucherEnum).isSameAs(VoucherEnum.FIXED);
            case PERCENT -> assertThat(voucherEnum).isSameAs(VoucherEnum.PERCENT);
        }
    }

    @Test
    @DisplayName("유요하지 않은 숫자가 들어올 경우 Optional.empty()를 반환한다.")
    void decideVoucherTypeEmpty() {
        assertThat(VoucherEnum.decideVoucherType(100)).isEqualTo(Optional.empty());
    }
}
