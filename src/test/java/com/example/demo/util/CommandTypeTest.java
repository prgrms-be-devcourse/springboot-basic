package com.example.demo.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"lists", "lists", "voucher"})
    @DisplayName("커멘드 명령어에 없는 명령어가 입력되면 예외를 발생시킨다.")
    void programMenuTest(String input) {
        assertThatThrownBy(() -> CommandType.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력하신 " + input + "는 유효한 커멘드가 아닙니다. \n exit, create, list 중 하나를 입력하세요.\n");
    }
}