package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class VoucherTypeTest {

    @ParameterizedTest
    @DisplayName("올바른 바우처 타입 선택 시 성공한다.")
    @ValueSource(strings = {"0", "1"})
    void getVoucherType_ParamVoucherTypeString_ReturnVoucherType(String input) {
        VoucherType voucherType = VoucherType.getVoucherType(input);

        assertDoesNotThrow(() -> voucherType);
    }

    @ParameterizedTest
    @DisplayName("부적절한 바우처 타입 선택 시 실패한다.")
    @ValueSource(strings = {"-1", "3", "12"})
    void getVoucherType_ParamWrongVoucherTypeString_Exception(String input) {
        Exception exception = catchException(() -> VoucherType.getVoucherType(input));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

}