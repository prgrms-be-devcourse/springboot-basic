package com.devcourse.voucherapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @DisplayName("존재하는 메뉴 번호 입력 시, 해당 메뉴 객체가 반환된다.")
    @Test
    void selectExistedMenuTest() {
        Menu createMenu = Menu.of("1");
        Menu listMenu = Menu.of("2");
        Menu updateMenu = Menu.of("3");
        Menu deleteMenu = Menu.of("4");
        Menu quitMenu = Menu.of("5");

        assertEquals(Menu.CREATE, createMenu);
        assertEquals(Menu.READ, listMenu);
        assertEquals(Menu.UPDATE, updateMenu);
        assertEquals(Menu.DELETE, deleteMenu);
        assertEquals(Menu.QUIT, quitMenu);
    }

    @DisplayName("존재하지 않는 메뉴 번호 입력 시, MenuInputException 예외가 발생한다.")
    @Test
    void selectNotExistedMenuTest() {
        assertThrows(MenuInputException.class, () -> Menu.of("10000"));
        assertThrows(MenuInputException.class, () -> Menu.of("string"));
    }
}
