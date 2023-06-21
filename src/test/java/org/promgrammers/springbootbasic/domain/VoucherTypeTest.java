package org.promgrammers.springbootbasic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"percent", "fixed"})
    @DisplayName("성공 - percent,fixed면 객체 생성")
    void creationSuccessTest(String voucherType) throws Exception {

        assertDoesNotThrow(() -> VoucherType.of(voucherType));
    }

    @ParameterizedTest
    @CsvSource(value = {"time", "place"})
    @DisplayName("실패 - percent,fixed외 조건")
    void creationFailTest(String voucherType) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> VoucherType.of(voucherType));
    }
}