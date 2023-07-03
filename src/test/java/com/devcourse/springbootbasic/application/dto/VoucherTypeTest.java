package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @ParameterizedTest
    @DisplayName("올바른 바우처 타입 선택 시 성공")
    @ValueSource(strings = {"1", "2"})
    void testGetVoucherType(String input) {
        assertDoesNotThrow(() -> VoucherType.getVoucherType(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 바우처 타입 선택 시 실패")
    @ValueSource(strings = {"0", "3", "12"})
    void testGetVoucherTypeException(String input) {
        assertThrows(InvalidDataException.class, () -> VoucherType.getVoucherType(input));
    }

}