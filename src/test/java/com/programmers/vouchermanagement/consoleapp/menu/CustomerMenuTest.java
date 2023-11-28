package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest(name = "{0}번을 받으면 {1}을 반환한다.")
    @MethodSource("stringAndCustomerMenuProvider")
    @DisplayName("입력 받은 메뉴 번호에 맞는 고객 메뉴를 반환한다.")
    void testFindMenuSuccessful(String input, CustomerMenu expectedMenu) {
        //when
        CustomerMenu customerMenu = CustomerMenu.findCustomerMenu(input);

        //then
        assertThat(customerMenu, is(expectedMenu));
    }

    static Stream<Arguments> stringAndCustomerMenuProvider() {
        return Stream.of(
                Arguments.of("1", CustomerMenu.CREATE),
                Arguments.of("2", CustomerMenu.LIST),
                Arguments.of("3", CustomerMenu.SEARCH),
                Arguments.of("4", CustomerMenu.UPDATE),
                Arguments.of("5", CustomerMenu.DELETE),
                Arguments.of("6", CustomerMenu.BLACKLIST),
                Arguments.of("7", CustomerMenu.SEARCH_VOUCHERS),
                Arguments.of("8", CustomerMenu.REMOVE_VOUCHER)
        );
    }
}
