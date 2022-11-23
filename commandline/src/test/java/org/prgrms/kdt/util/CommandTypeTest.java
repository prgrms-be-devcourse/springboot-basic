package org.prgrms.kdt.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.exception.WrongCommandException;
import org.prgrms.kdt.io.CommandType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommandTypeTest {

    @ParameterizedTest
    @DisplayName("입력에 맞는 명령어를 리턴한다.")
    @MethodSource("enumAndStringProvider")
    void selectTypeCorrectTest(CommandType type, String input) {
        assertEquals(CommandType.selectType(input), type);
    }

    static Stream<Arguments> enumAndStringProvider() {
        return Stream.of(
                arguments(CommandType.CREAT, "create"),
                arguments(CommandType.LIST, "list"),
                arguments(CommandType.EXIT, "exit")
        );
    }

    @Test
    @DisplayName("제시된 선택지 외의 명령어는 올 수 없다.")
    void selectTypeIncorrectTest() {
        assertThrows(WrongCommandException.class, () -> {
            CommandType.selectType("give");
        });
    }
}