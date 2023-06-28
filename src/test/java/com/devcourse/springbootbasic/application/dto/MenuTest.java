package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {

    @ParameterizedTest
    @DisplayName("올바른 메뉴 입력하면 성공")
    @ValueSource(strings = {"1", "2", "0", "exit", "list", "create"})
    void menuTest(String input) {
        assertDoesNotThrow(() -> Menu.getMenu(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 메뉴 입력하면 실패")
    @ValueSource(strings = {"13", "14", "가나다", "absd", "a", "ㄱ", "?"})
    void menuExceptionTest(String input) {
        assertThrows(InvalidDataException.class, () -> Menu.getMenu(input));
    }

}