package com.zerozae.voucher.validator;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.exception.ExceptionHandler;
import com.zerozae.voucher.mock.MockMessageSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidatorTest {
    InputValidator inputValidator;
    MessageConverter messageConverter;

    @BeforeEach
    void setUp(){
        inputValidator = new InputValidator();
        messageConverter = new MessageConverter(new MockMessageSource());
    }

    @Test
    @DisplayName("숫자 입력 검증 시 숫자 입력 성공 테스트")
    public void validateInputDiscountSuccessTest() {
        // Given
        String validInput = "10";

        // When
        Long result = InputValidator.validateInputDiscount(validInput);

        // Then
        assertEquals(result, Long.parseLong(validInput));
    }

    @Test
    @DisplayName("숫자 입력 검증 시 문자열 입력 실패 테스트")
    void validateInputDiscountFailedTest() {
        // Given
        String validInput = "test";

        // When

        // Then
        assertThrows(ExceptionHandler.class, () -> {
            InputValidator.validateInputDiscount(validInput);
        });
    }
    @Test
    @DisplayName("문자열 입력 검증 시 문자 입력 성공 테스트")
    void validateInputStringSuccessTest() {
        // Given
        String validInput = "test";

        // When
        String result = InputValidator.validateInputString(validInput);

        // Then
        assertEquals(result, validInput);
    }

    @Test
    @DisplayName("문자열 입력 검증 시 숫자 입력 실패 테스트")
    void validateInputStringFailedTest(){
        // Given
        String validInput = "42";

        // When

        // Then
        assertThrows(ExceptionHandler.class, () -> {
            InputValidator.validateInputString(validInput);
        });
    }
}
