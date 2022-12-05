package org.prgrms.springorder.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.console.io.Console;
import org.prgrms.springorder.console.io.ConsoleInput;
import org.prgrms.springorder.console.io.Input;
import org.prgrms.springorder.console.io.Output;

@TestInstance(Lifecycle.PER_CLASS)
class ConsoleTest {

    @DisplayName("inputStringToLong 테스트 - 입력을 받아 입력값을 long 으로  리턴한다.")
    @Test
    void inputStringToLongTest() {
        //given
        String userInput = "10";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        Input consoleInput = new ConsoleInput(
            new BufferedReader(new InputStreamReader(inputStream)));
        Output mockOutput = mock(Output.class);
        Console console = new Console(consoleInput, mockOutput);

        //when
        long inputStringToLong = console.inputStringToLong();
        //then
        assertEquals(10L, inputStringToLong);

    }

    @DisplayName("inputStringToLong 예외 테스트 - 입력을 받아 입력값이 long이 아니면 예외를 던진다. ")
    @Test
    void inputStringToLongFailTestThrowException() {
        //given
        String userInput = "not Long";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        Input consoleInput = new ConsoleInput(
            new BufferedReader(new InputStreamReader(inputStream)));
        Output mockOutput = mock(Output.class);
        Console console = new Console(consoleInput, mockOutput);
        //when & then
        assertThrows(IllegalArgumentException.class, console::inputStringToLong);
    }

    @DisplayName("showMessage 테스트 - 파라미터로 넘어온 메시지 여러건을 건 마다 개행문자와 함께 출력한다.")
    @Test
    void showMessagesListTest() {
        //given
        List<String> messages = List.of("message1", "message2", "message3");

        Input mockConsoleInput = mock(Input.class);
        Output mockConsoleOutput = mock(Output.class);
        Console console = new Console(mockConsoleInput, mockConsoleOutput);

        doNothing().when(mockConsoleOutput)
            .showMessages(messages.toArray(String[]::new));

        //when
        console.showMessages(messages);
        //then
        verify(mockConsoleOutput).showMessages(messages.toArray(String[]::new));
    }

    private InputStream generateUserInput(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    private InputStream createInputStreamSequence(String... inputs) {
        List<InputStream> inputStreams = Arrays.stream(inputs)
            .map(input -> this.generateUserInput(input + "\n"))
            .collect(Collectors.toList());

        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }

}