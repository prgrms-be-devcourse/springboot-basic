package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CustomerCommandTest {
    @ParameterizedTest
    @DisplayName("올바른 고객 관리 메뉴인 경우 성공한다.")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void 올바른_고객_관리_메뉴(int number) {
        assertThatCode(() -> CustomerCommand.findByNumber(number))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("잘못된 고객 관리 메뉴인 경우 예외가 발생한다.")
    @ValueSource(ints = {0, 999, -10})
    void 잘못된_고객_관리_메뉴(int command) {
        assertThatThrownBy(() -> CustomerCommand.findByNumber(command))
                .isInstanceOf(InvalidCommandException.class)
                .hasMessage("존재하지 않는 명령어입니다.");
    }
}
