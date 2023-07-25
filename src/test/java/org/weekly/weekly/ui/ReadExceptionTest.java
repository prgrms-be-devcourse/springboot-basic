package org.weekly.weekly.ui;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.weekly.weekly.ui.exception.InputValidator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadExceptionTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 사용자가_빈값이나_입력오류났을때_예외발생(String userInput) {
        assertThatThrownBy(() -> InputValidator.isEmpty(userInput))
                .isInstanceOf(RuntimeException.class);
    }
}
