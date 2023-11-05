package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest(name = "{0}번을 받으면 {1}을 반환한다.")
    @MethodSource("stringAndMenuProvider")
    @DisplayName("입력 받은 메뉴 번호에 맞는 메뉴를 반환한다.")
    void testFindMenuSuccessful(String input, Menu expectedMenu) {
        //when
        Menu menu = Menu.findMenu(input);

        //then
        assertThat(menu, is(expectedMenu));
    }

    static Stream<Arguments> stringAndMenuProvider() {
        return Stream.of(
                Arguments.of("0", Menu.EXIT),
                Arguments.of("1", Menu.VOUCHER),
                Arguments.of("2", Menu.CUSTOMER)
        );
    }
}
