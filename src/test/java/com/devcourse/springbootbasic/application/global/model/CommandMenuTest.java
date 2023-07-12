package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandMenuTest {

    @ParameterizedTest
    @DisplayName("올바른 메뉴 입력하면 성공한다.")
    @ValueSource(strings = {"0", "1", "2", "3", "4", "5"})
    void getMenu_ParamMenuString_ReturnMenu(String input) {
        assertDoesNotThrow(() -> CommandMenu.getCommandMenu(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 메뉴 입력하면 실패한다.")
    @ValueSource(strings = {"13", "14", "가나다", "absd", "a", "ㄱ", "?"})
    void getMenu_ParamWrongMenuString_Exception(String input) {
        assertThrows(InvalidDataException.class, () -> CommandMenu.getCommandMenu(input));
    }

}