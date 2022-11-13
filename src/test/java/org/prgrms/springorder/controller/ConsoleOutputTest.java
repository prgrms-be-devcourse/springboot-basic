package org.prgrms.springorder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsoleOutputTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private ConsoleOutput consoleOutput;

    @BeforeAll
    public void setUp() {
        PrintStream printStream = new PrintStream(output);
        consoleOutput = new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(printStream)));
    }

    @AfterEach
    void restoreStreams() {
        output.reset();
    }

    @DisplayName("showMessage 테스트 - 파라미터로 넘어온 메시지 단건을 개행문자와 함께 출력한다.")
    @ParameterizedTest
    @ValueSource(strings = {"value1", "value2", "value3", "message", "message2", "message3"})
    void showMessageTest(String message) {
        //given
        String expectedMessage = message + "\n";
        //when
        consoleOutput.showMessage(message);
        //then
        assertEquals(expectedMessage, output.toString());
    }

    @DisplayName("showMessage 테스트 - 파라미터로 넘어온 메시지 여러건을 건 마다 개행문자와 함께 출력한다.")
    @Test
    void showMessageTest() {
        //given
        String[] messages = {"message1", "message2", "message3"};

        StringBuilder stringBuilder = new StringBuilder();

        for (String message : messages) {
            stringBuilder.append(message).append("\n");
        }
        String expectedMessage = stringBuilder.toString();

        //when
        consoleOutput.showMessages(messages);
        //then
        assertEquals(expectedMessage, output.toString());
    }

}