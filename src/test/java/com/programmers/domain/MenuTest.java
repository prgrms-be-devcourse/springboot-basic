package com.programmers.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @DisplayName("입력에 맞는 메뉴를 찾는다")
    @Test
    void findMenu() {
        //given
        String input = "create";

        //when
        Menu result = Menu.findMenu(input);

        //then
        Assertions.assertThat(result).isEqualTo(Menu.CREATE);
    }

    @DisplayName("메뉴가 입력되지 않으면 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void findMenuWithEmptyInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> Menu.findMenu(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력된 메뉴가 형식에 맞지 않으면 예외처리한다")
    @ValueSource(strings = { "ab", " ", "22", "c"})
    @ParameterizedTest
    void findMenuWithWrongInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> Menu.findMenu(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}