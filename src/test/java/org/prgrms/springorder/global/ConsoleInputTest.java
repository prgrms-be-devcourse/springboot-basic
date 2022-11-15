package org.prgrms.springorder.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ConsoleInputTest {

    private ConsoleInput consoleInput;

    @DisplayName("inputString 테스트 - 입력을 받아 입력값을 리턴한다.")
    @Test
    void inputStringTest() {
        //given
        String userInput = "input";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        consoleInput = new ConsoleInput(new BufferedReader(new InputStreamReader(inputStream)));
        //when
        String inputString = consoleInput.input();
        //then
        assertEquals(userInput, inputString);
    }

    @DisplayName("inputString 예외 테스트 - 공백을 입력하면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n"})
    void inputStringTestThrowException(String userInput) {
        //given
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        consoleInput = new ConsoleInput(new BufferedReader(new InputStreamReader(inputStream)));
        //when & then
        assertThrows(IllegalArgumentException.class, () -> consoleInput.input());
    }

    private InputStream generateUserInput(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

}