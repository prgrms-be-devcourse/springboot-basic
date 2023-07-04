package com.devcourse.springbootbasic.application.model;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {

    @ParameterizedTest
    @DisplayName("올바른 메뉴 입력하면 성공한다.")
    @ValueSource(strings = {"1", "2", "0", "exit", "list", "create"})
    void getMenu_ParamMenuString_ReturnMenu(String input) {
        assertDoesNotThrow(() -> Menu.getMenu(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 메뉴 입력하면 실패한다.")
    @ValueSource(strings = {"13", "14", "가나다", "absd", "a", "ㄱ", "?"})
    void getMenu_ParamWrongMenuString_Exception(String input) {
        assertThrows(InvalidDataException.class, () -> Menu.getMenu(input));
    }

}