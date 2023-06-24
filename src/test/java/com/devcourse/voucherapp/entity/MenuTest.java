package com.devcourse.voucherapp.entity;

import static com.devcourse.voucherapp.entity.Menu.getMenu;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @DisplayName("존재하는 메뉴를 입력했을 때, 해당 메뉴 객체가 반환되는지 테스트")
    @Test
    void selectExistedMenuTest() {
        Menu createMenu = getMenu("1");
        Menu listMenu = getMenu("2");

        assertEquals(Menu.CREATE, createMenu);
        assertEquals(Menu.LIST, listMenu);
    }

    @DisplayName("존재하지 않는 메뉴를 입력했을 때, 예외처리 수행 테스트")
    @Test
    void selectNotExistedMenuTest() {
        assertThrows(MenuInputException.class, () -> getMenu("10000"));
        assertThrows(MenuInputException.class, () -> getMenu("string"));
    }
}
