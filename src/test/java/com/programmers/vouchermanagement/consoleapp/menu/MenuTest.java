package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("없는 메뉴 종류를 선택할 시 INCORRECT_MENU를 반환한다.")
    void testFindMenuSuccessful_ReturnIncorrectMenu() {
        //given
        String desiredMenu = "Invalid";

        //when
        Menu menu = Menu.findMenu(desiredMenu);

        //then
        assertThat(menu.isIncorrect(), is(true));
    }

    @Test
    @DisplayName("입력 받은 메뉴 번호에 맞는 메뉴를 반환한다.")
    void testFindMenuSuccessful() {
        //given
        String menuInput = "1";
        String exit = "0";

        //when
        Menu menu = Menu.findMenu(menuInput);
        Menu exitMenu = Menu.findMenu(exit);

        //then
        assertThat(menu, is(Menu.VOUCHER));
        assertThat(exitMenu.isExit(), is(true));
    }
}
