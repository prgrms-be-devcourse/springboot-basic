package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

class VoucherTypeTest {

    @DisplayName("유저의 커맨드 입력으로 VoucherType을 가져올 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1, FIXED", "2, PERCENT"})
    void canGetVoucherTypeWithUserInput(String command, VoucherType voucherType) {
        assertEquals(voucherType, VoucherType.of(command));
    }
}
