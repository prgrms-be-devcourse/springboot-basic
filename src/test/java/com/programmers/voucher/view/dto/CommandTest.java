package com.programmers.voucher.view.dto;

import com.programmers.voucher.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CommandTest {
    @ParameterizedTest
    @DisplayName("올바른 명령어인 경우 성공한다.")
    @ValueSource(strings = {"exit", "create", "list"})
    void 올바른_명령어(String command) {
        assertThatCode(() -> Command.findByName(command))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("잘못된 명령어인 경우 예외가 발생한다.")
    @ValueSource(strings = {"a", "123", ""})
    void 잘못된_명령어(String command) {
        assertThatThrownBy(() -> Command.findByName(command))
                .isInstanceOf(InvalidCommandException.class);
    }
}
