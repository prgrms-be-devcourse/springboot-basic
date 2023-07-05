package com.demo.voucher.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTypeTest {

    private static Stream<Arguments> commandTypes() {
        return Stream.of(
                Arguments.of("생성", CommandType.CREATE),
                Arguments.of("목록", CommandType.LIST),
                Arguments.of("종료", CommandType.EXIT)
        );
    }


    @ParameterizedTest
    @MethodSource("commandTypes")
    @DisplayName("올바른 Command Type Input을 입력했을 때 COMMAND_MAP으로부터 Command Type Enum 객체를 정상적으로 탐색하여 가져오는지 확인하는 테스트")
    void getOf_성공(String input, CommandType commandType) {
        System.out.println(CommandType.of(input));
        assertEquals(CommandType.of(input), commandType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "list", "exit", "\n", "1", "2", "3", " "})
    @DisplayName("올바른 Command Type Input을 입력하지 않았을 때 IllegarArgumentException이 발생하는 것을 검증하는 테스트")
    void getOf_실패(String input) {
        // given
        String expectedMessage = "올바른 명령어를 입력하지 않았습니다.";

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> CommandType.of(input));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }
}