package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerMenuTest {

    @Test
    @DisplayName("없는 바우처 메뉴를 선택할 시 INCORRECT_MENU를 반환한다.")
    void testFindMenuSuccessful_ReturnIncorrectMenu() {
        //given
        String desiredMenu = "Invalid";

        //when
        CustomerMenu menu = CustomerMenu.findCustomerMenu(desiredMenu);

        //then
        assertThat(menu, is(CustomerMenu.INCORRECT_MENU));
    }

    @Test
    @DisplayName("입력 받은 메뉴 번호에 맞는 고객 메뉴를 반환한다.")
    void testFindMenuSuccessful() {
        //given
        String createMenu = "1";
        String listMenu = "2";
        String searchMenu = "3";
        String updateMenu = "4";
        String deleteMenu = "5";
        String blacklistMEnu = "6";
        String searchVoucherMenu = "7";
        String removeVoucherMenu = "8";

        //when
        CustomerMenu create = CustomerMenu.findCustomerMenu(createMenu);
        CustomerMenu list = CustomerMenu.findCustomerMenu(listMenu);
        CustomerMenu search = CustomerMenu.findCustomerMenu(searchMenu);
        CustomerMenu update = CustomerMenu.findCustomerMenu(updateMenu);
        CustomerMenu delete = CustomerMenu.findCustomerMenu(deleteMenu);
        CustomerMenu blacklist = CustomerMenu.findCustomerMenu(blacklistMEnu);
        CustomerMenu searchVoucher = CustomerMenu.findCustomerMenu(searchVoucherMenu);
        CustomerMenu removeVoucher = CustomerMenu.findCustomerMenu(removeVoucherMenu);

        //then
        assertThat(create, is(CustomerMenu.CREATE));
        assertThat(list, is(CustomerMenu.LIST));
        assertThat(search, is(CustomerMenu.SEARCH));
        assertThat(update, is(CustomerMenu.UPDATE));
        assertThat(delete, is(CustomerMenu.DELETE));
        assertThat(blacklist, is(CustomerMenu.BLACKLIST));
        assertThat(searchVoucher, is(CustomerMenu.SEARCH_VOUCHERS));
        assertThat(removeVoucher, is(CustomerMenu.REMOVE_VOUCHER));
    }
}
