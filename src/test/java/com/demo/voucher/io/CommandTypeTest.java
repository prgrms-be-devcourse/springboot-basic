package com.demo.voucher.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"exit", "create", "list"})
    @DisplayName("입력 받는 명령어 메뉴가 올바른 경우 테스트")
    void isValidCommandInput_메서드_올바른메뉴_입력테스트(String commandInput) {
        Assertions.assertTrue(CommandType.isValidCommandInput(commandInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"exitt", "0", "voucher", "2"})
    @DisplayName("입력 받는 명령어 메뉴가 올바르지 않은 경우 테스트")
    void isValidCommandInput_메서드_올바르지_않은_메뉴_입력테스트(String commandInput) {
        Assertions.assertFalse(CommandType.isValidCommandInput(commandInput));
    }

    @Test
    void getCommand() {
    }

    @Test
    @DisplayName("CommandType의 오버라이딩한 toString이 잘 작동하는지 확인하는 테스트")
    void testToString_메서드() {
        List<String> expectedCommands = List.of(
                "Type exit to exit the program.",
                "Type create to create a new voucher.",
                "Type list to list all vouchers."
        );

        List<String> commands = Arrays.stream(CommandType.values())
                .map(CommandType::toString).toList();

        assertThat(commands).isEqualTo(expectedCommands);
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}