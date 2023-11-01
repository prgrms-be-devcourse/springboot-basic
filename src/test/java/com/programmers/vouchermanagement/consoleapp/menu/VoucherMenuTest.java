package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherMenuTest {

    @Test
    @DisplayName("없는 바우처 메뉴를 선택할 시 INCORRECT_MENU를 반환한다.")
    void testFindMenuSuccessful_ReturnIncorrectMenu() {
        //given
        String desiredMenu = "Invalid";

        //when
        VoucherMenu menu = VoucherMenu.findVoucherMenu(desiredMenu);

        //then
        assertThat(menu, is(VoucherMenu.INCORRECT_MENU));
    }

    @Test
    @DisplayName("입력 받은 메뉴 번호에 맞는 바우처 메뉴를 반환한다.")
    void testFindMenuSuccessful() {
        //given
        String createMenu = "1";
        String listMenu = "2";
        String searchMenu = "3";
        String updateMenu = "4";
        String deleteMenu = "5";
        String grantMenu = "6";
        String searchOwnerMenu = "7";

        //when
        VoucherMenu create = VoucherMenu.findVoucherMenu(createMenu);
        VoucherMenu list = VoucherMenu.findVoucherMenu(listMenu);
        VoucherMenu search = VoucherMenu.findVoucherMenu(searchMenu);
        VoucherMenu update = VoucherMenu.findVoucherMenu(updateMenu);
        VoucherMenu delete = VoucherMenu.findVoucherMenu(deleteMenu);
        VoucherMenu grant = VoucherMenu.findVoucherMenu(grantMenu);
        VoucherMenu searchOwner = VoucherMenu.findVoucherMenu(searchOwnerMenu);

        //then
        assertThat(create, is(VoucherMenu.CREATE));
        assertThat(list, is(VoucherMenu.LIST));
        assertThat(search, is(VoucherMenu.SEARCH));
        assertThat(update, is(VoucherMenu.UPDATE));
        assertThat(delete, is(VoucherMenu.DELETE));
        assertThat(grant, is(VoucherMenu.GRANT));
        assertThat(searchOwner, is(VoucherMenu.SEARCH_OWNER));
    }
}
