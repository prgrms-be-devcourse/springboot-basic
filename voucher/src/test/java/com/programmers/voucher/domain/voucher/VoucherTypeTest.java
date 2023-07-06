package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.domain.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void decideVoucherType(int number) {
        VoucherType voucherType = VoucherType.decideVoucherType(number).get();
        switch (voucherType) {
            case FIXED -> assertThat(voucherType).isSameAs(VoucherType.FIXED);
            case PERCENT -> assertThat(voucherType).isSameAs(VoucherType.PERCENT);
        }
    }

    @Test
    @DisplayName("유요하지 않은 숫자가 들어올 경우 Optional.empty()를 반환한다.")
    void decideVoucherTypeEmpty() {
        assertThat(VoucherType.decideVoucherType(100)).isEqualTo(Optional.empty());
    }
}
