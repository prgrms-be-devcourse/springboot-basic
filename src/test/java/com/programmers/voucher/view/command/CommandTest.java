package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CommandTest {
    @ParameterizedTest
    @DisplayName("올바른 명령어 메뉴인 경우 성공한다.")
    @ValueSource(ints = {1, 2, 3})
    void 올바른_명령어_메뉴(int number) {
        assertThatCode(() -> Command.findByNumber(number))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("잘못된 명령어 메뉴인 경우 예외가 발생한다.")
    @ValueSource(ints = {0, 999, -10})
    void 잘못된_명령어_메뉴(int command) {
        assertThatThrownBy(() -> Command.findByNumber(command))
                .isInstanceOf(InvalidCommandException.class)
                .hasMessage("존재하지 않는 명령어입니다.");
    }
}
