package com.devcourse.voucherapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @DisplayName("존재하는 메뉴 입력 시, 해당 메뉴 객체가 반환된다.")
    @ParameterizedTest
    @CsvSource(value = {"1,VOUCHER", "2,CUSTOMER", "quit,QUIT"})
    void selectExistedMenuTest(String menuNumber, Menu menu) {
        assertEquals(menu, Menu.from(menuNumber));
    }

    @DisplayName("존재하지 않는 메뉴 입력 시, MenuInputException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"10000", "string"})
    void selectNotExistedMenuTest(String invalidMenuNumber) {
        assertThrows(MenuInputException.class, () -> Menu.from(invalidMenuNumber));
    }
}
