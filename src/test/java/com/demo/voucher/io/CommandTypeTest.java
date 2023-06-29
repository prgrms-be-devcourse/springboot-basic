package com.demo.voucher.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTest {

    private static Stream<Arguments> commandTypes() {
        return Stream.of(
                Arguments.of("create", CommandType.CREATE),
                Arguments.of("list", CommandType.LIST),
                Arguments.of("exit", CommandType.EXIT)
        );
    }


    @ParameterizedTest
    @ValueSource(strings = {"exit", "create", "list"})
    @DisplayName("입력 받는 명령어 메뉴가 올바른 경우 테스트")
    void isValidCommandInput_메서드_올바른메뉴_입력테스트(String commandInput) {
        assertTrue(CommandType.isValidCommandInput(commandInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"exitt", "0", "voucher", "2"})
    @DisplayName("입력 받는 명령어 메뉴가 올바르지 않은 경우 테스트")
    void isValidCommandInput_메서드_올바르지_않은_메뉴_입력테스트(String commandInput) {
        assertFalse(CommandType.isValidCommandInput(commandInput));
    }

    @ParameterizedTest
    @MethodSource("commandTypes")
    @DisplayName("올바른 Command Type Input을 입력했을 때 COMMAND_MAP으로부터 Command Type Enum 객체를 정상적으로 탐색하여 가져오는지 확인하는 테스트")
    void getCommandType(String input, CommandType commandType) {
        System.out.println(CommandType.getCommandType(input));
        assertEquals(CommandType.getCommandType(input), commandType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"creat", "lst", " ", "\n", "1", "2", "0", "생성"})
    @DisplayName("올바른 Command Type Input을 입력하지 않았을 때 COMMAND_MAP으로부터 Command Type Enum 객체를 탐색하지 못하여 실패하는 테스트")
    void getCommandType_fail(String input) {
        assertFalse(CommandType.isValidCommandInput(input));
    }
}