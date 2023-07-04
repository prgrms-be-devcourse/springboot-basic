package com.devcourse.springbootbasic.application.model;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @ParameterizedTest
    @DisplayName("올바른 바우처 타입 선택 시 성공한다.")
    @ValueSource(strings = {"1", "2"})
    void getVoucherType_ParamVoucherTypeString_ReturnVoucherType(String input) {
        assertDoesNotThrow(() -> VoucherType.getVoucherType(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 바우처 타입 선택 시 실패한다.")
    @ValueSource(strings = {"0", "3", "12"})
    void getVoucherType_ParamWrongVoucherTypeString_Exception(String input) {
        assertThrows(InvalidDataException.class, () -> VoucherType.getVoucherType(input));
    }

}