package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest(name = "{0}을 받으면 {1}을 출력한다.")
    @MethodSource("stringAndVoucherMenuProvider")
    @DisplayName("입력 받은 메뉴 번호에 맞는 바우처 메뉴를 반환한다.")
    void testFindMenuSuccessful(String input, VoucherMenu expectedMenu) {
        //when
        VoucherMenu menu = VoucherMenu.findVoucherMenu(input);

        //then
        assertThat(menu, is(expectedMenu));
    }

    static Stream<Arguments> stringAndVoucherMenuProvider() {
        return Stream.of(
                Arguments.of("1", VoucherMenu.CREATE),
                Arguments.of("2", VoucherMenu.LIST),
                Arguments.of("3", VoucherMenu.SEARCH),
                Arguments.of("4", VoucherMenu.UPDATE),
                Arguments.of("5", VoucherMenu.DELETE),
                Arguments.of("6", VoucherMenu.GRANT),
                Arguments.of("7", VoucherMenu.SEARCH_OWNER)
        );
    }
}
