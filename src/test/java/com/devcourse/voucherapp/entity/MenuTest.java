package com.devcourse.voucherapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @DisplayName("존재하는 메뉴 입력 시, 해당 메뉴 객체가 반환된다.")
    @Test
    void selectExistedMenuTest() {
        Menu createMenu = Menu.from("1");
        Menu listMenu = Menu.from("2");
        Menu updateMenu = Menu.from("3");
        Menu deleteMenu = Menu.from("4");
        Menu quitMenu = Menu.from("quit");

        assertEquals(Menu.CREATE, createMenu);
        assertEquals(Menu.READ, listMenu);
        assertEquals(Menu.UPDATE, updateMenu);
        assertEquals(Menu.DELETE, deleteMenu);
        assertEquals(Menu.QUIT, quitMenu);
    }

    @DisplayName("존재하지 않는 메뉴 입력 시, MenuInputException 예외가 발생한다.")
    @Test
    void selectNotExistedMenuTest() {
        assertThrows(MenuInputException.class, () -> Menu.from("10000"));
        assertThrows(MenuInputException.class, () -> Menu.from("string"));
    }
}
