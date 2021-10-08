package com.prgrms.w3springboot.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    @DisplayName("유효한 커맨드 입력인지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"create", "list", "exit"})
    void testInputCommand(String command) {
        assertThat(CommandType.of(command))
                .isIn(CommandType.values());
    }

    @DisplayName("유효하지 않은 커맨드 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Create", "Exit", "lisT", " list", "create ", "", " "})
    void testInputCommandException(String command) {
        assertThatThrownBy(() -> CommandType.of(command))
                .isInstanceOf(IllegalArgumentException.class);
    }
}