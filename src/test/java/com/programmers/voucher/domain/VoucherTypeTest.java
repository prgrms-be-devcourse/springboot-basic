package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VoucherTypeTest {

    @DisplayName("유저의 커맨드 입력으로 VoucherType을 가져올 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1, FIXED", "2, PERCENT"})
    void canGetVoucherTypeWithUserInput(String command, VoucherType voucherType) {
        assertEquals(voucherType, VoucherType.of(command));
    }

    @DisplayName("올바르지 않은 커맨드가 입력되면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"3", "4", "FIXED", "PERCENT", "fixed", "percent"})
    void getVoucherTypeFailTest(String command) {
        assertThatThrownBy(() -> VoucherType.of(command))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
