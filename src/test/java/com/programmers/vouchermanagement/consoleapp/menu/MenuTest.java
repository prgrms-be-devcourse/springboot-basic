package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("없는 메뉴 종류를 선택할 시 INCORRECT_MENU를 반환한다.")
    void testFindMenuFailed_ReturnIncorrectMenu() {
        //given
        final String desiredMenu = "Invalid";

        //when
        final Menu menu = Menu.findMenu(desiredMenu);

        //then
        assertThat(menu, is(Menu.INCORRECT_MENU));
    }

    @Test
    @DisplayName("입력 받은 메뉴 번호에 맞는 메뉴를 반환한다.")
    void testFindMenuSuccessful() {
        //given
        final String menuInput = "1";

        //when
        final Menu menu = Menu.findMenu(menuInput);

        //then
        assertThat(menu, is(Menu.VOUCHER));
    }
}
