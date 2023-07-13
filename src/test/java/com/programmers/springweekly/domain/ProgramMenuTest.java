package com.programmers.springweekly.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProgramMenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"show", "whitelist", "juice"})
    @DisplayName("프로그램 메뉴에 없는 메뉴가 입력되면 예외를 발생시킨다.")
    void programMenuTest(String input) {
        assertThatThrownBy(() -> ProgramMenu.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 프로그램 메뉴가 없습니다.");
    }

}
