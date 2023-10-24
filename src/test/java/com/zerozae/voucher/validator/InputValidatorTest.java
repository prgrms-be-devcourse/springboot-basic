package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidatorTest {

    @Test
    @DisplayName("숫자 입력 검증 시 숫자 입력 성공 테스트")
    public void validateInputNumber_Success_Test() {
        // Given
        String validInput = "10";

        // When
        Long result = InputValidator.validateInputNumber(validInput);

        // Then
        assertEquals(result, Long.parseLong(validInput));
    }

    @Test
    @DisplayName("숫자 입력 검증 시 문자열 입력 실패 테스트")
    void validateInputNumber_inputString_Failed_Test() {
        // Given
        String validInput = "test";

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            InputValidator.validateInputNumber(validInput);
        });
    }

    @Test
    @DisplayName("문자열 입력 검증 시 문자 입력 성공 테스트")
    void validateInputString_Success_Test() {
        // Given
        String validInput = "test";

        // When
        String result = InputValidator.validateInputString(validInput);

        // Then
        assertEquals(result, validInput);
    }

    @Test
    @DisplayName("문자열 입력 검증 시 숫자 입력 실패 테스트")
    void validateInputString_inputNumber_Failed_Test() {
        // Given
        String validInput = "42";

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            InputValidator.validateInputString(validInput);
        });
    }
}
