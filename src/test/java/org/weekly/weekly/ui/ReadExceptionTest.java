package org.weekly.weekly.ui;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadExceptionTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 사용자가_빈값이나_입력오류났을때_예외발생(String userInput) {
        assertThatThrownBy(()->ReadException.isEmpty(userInput))
                .isInstanceOf(RuntimeException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void 메뉴외의_숫자값을_입력하면_예외발생(String userInput) {
        assertThatThrownBy(()->ReadException.notInputFormat(userInput))
                .isInstanceOf(RuntimeException.class);
    }
}
