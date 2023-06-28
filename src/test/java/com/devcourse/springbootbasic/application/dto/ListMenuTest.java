package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ListMenuTest {

    @ParameterizedTest
    @DisplayName("올바른 출력 메뉴 선택하면 성공")
    @ValueSource(strings = {"1", "2"})
    void 출력메뉴테스트(String input) {
        assertDoesNotThrow(() -> ListMenu.getListMenu(input));
    }

    @ParameterizedTest
    @DisplayName("부적절한 출력 메뉴 선택하면 실패")
    @ValueSource(strings = {"0", "3", "12", "-1"})
    void 출력메뉴예외테스트(String input) {
        assertThrows(InvalidDataException.class, () -> ListMenu.getListMenu(input));
    }
}