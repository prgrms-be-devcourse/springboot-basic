package com.prgrms.w3springboot.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class VoucherTypeTest {

    @DisplayName("유효한 바우처 타입인지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"fixed", "percent"})
    void testVoucherType(String voucherType) {
        assertThat(VoucherType.of(voucherType))
                .isIn(VoucherType.values());
    }

    @DisplayName("유효하지 않은 바우처 타입을 입력받을 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"fixed ", " percent", "FIXED", "PERCENT", "fiXed", "Percent", " ", ""})
    void testVoucherTypeException(String voucherType) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> VoucherType.of(voucherType));
    }
}