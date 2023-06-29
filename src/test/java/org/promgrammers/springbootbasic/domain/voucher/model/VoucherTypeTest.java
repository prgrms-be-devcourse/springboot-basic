package org.promgrammers.springbootbasic.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1", "2"})
    @DisplayName("성공 - [1] percent,[2] fixed면 객체 생성")
    void creationSuccessTest(String voucherType) throws Exception {

        assertDoesNotThrow(() -> VoucherType.of(voucherType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "time", " ", ""})
    @DisplayName("실패 - [1] percent, [2] fixed외 조건")
    void creationFailTest(String voucherType) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> VoucherType.of(voucherType));
    }
}