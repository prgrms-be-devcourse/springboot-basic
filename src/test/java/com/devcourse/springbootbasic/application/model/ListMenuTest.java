package com.devcourse.springbootbasic.application.model;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListMenuTest {

    @ParameterizedTest
    @DisplayName("올바른 출력 메뉴 선택하면 성공한다.")
    @ValueSource(strings = {"1", "2"})
    void GetListMenu_ParamListMenuString_ReturnListMenu(String input) {
        var result = ListMenu.getListMenu(input);
        assertDoesNotThrow(() -> result);
    }

    @ParameterizedTest
    @DisplayName("부적절한 출력 메뉴 선택하면 실패한다.")
    @ValueSource(strings = {"0", "3", "12", "-1"})
    void GetListMenu_ParamWrongListMenuString_Exception(String input) {
        assertThrows(InvalidDataException.class, () -> ListMenu.getListMenu(input));
    }
}