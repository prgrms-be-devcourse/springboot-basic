package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.domain.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource({"1,FIXED", "2,PERCENT"})
    void decideVoucherType(int number, VoucherType expeceted) {
        VoucherType actual = VoucherType.decideVoucherType(number).get();
        assertThat(actual).isEqualTo(expeceted);
    }

    @Test
    @DisplayName("유효하지 않은 숫자가 들어올 경우 Optional.empty()를 반환한다.")
    void decideVoucherTypeEmpty() {
        // given
        int number = 100;

        // when
        Optional<VoucherType> optionalVoucherType = VoucherType.decideVoucherType(number);

        // then
        assertThat(optionalVoucherType).isEmpty();
    }
}
