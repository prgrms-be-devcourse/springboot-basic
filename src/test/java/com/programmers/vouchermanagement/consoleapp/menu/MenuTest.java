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
    @DisplayName("Parameter로 넘겨 받은 문자열이 대소문자 혼용일 경우 INCORRECT_MENU를 반환한다.")
    void testFindMenuFailed_CaseInsensitive() {
        //given
        final String selectExit = "Exit";
        final String selectList = "LIST";

        //when
        final Menu exitMenu = Menu.findMenu(selectExit);
        final Menu listMenu = Menu.findMenu(selectList);

        //then
        assertThat(exitMenu, is(Menu.INCORRECT_MENU));
        assertThat(listMenu, is(Menu.INCORRECT_MENU));
    }

    @Test
    @DisplayName("Parameter로 넘겨 받은 소문자 문자열과 비교해 맞는 메뉴를 반환한다.")
    void testFindMenuSuccessful_LowerCase() {
        //given
        final String selectCreate = "create";
        final String selectBlacklist = "blacklist";

        //when
        final Menu createMenu = Menu.findMenu(selectCreate);
        final Menu blacklistMenu = Menu.findMenu(selectBlacklist);

        //then
        assertThat(createMenu, is(Menu.CREATE));
        assertThat(blacklistMenu, is(Menu.BLACKLIST));
    }
}
