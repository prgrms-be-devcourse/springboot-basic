package org.prgrms.springorder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;

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
        String inputString = consoleInput.inputString();
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
        assertThrows(IllegalArgumentException.class, () -> consoleInput.inputString());
    }

    @DisplayName("inputStringToLong 테스트 - 입력을 받아 입력값을 long 으로  리턴한다.")
    @Test
    void inputStringToLongTest() {
        //given
        String userInput = "10";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        consoleInput = new ConsoleInput(new BufferedReader(new InputStreamReader(inputStream)));
        //when
        long inputStringToLong = consoleInput.inputStringToLong();
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

        consoleInput = new ConsoleInput(new BufferedReader(new InputStreamReader(inputStream)));
        //when & then
        assertThrows(IllegalArgumentException.class, consoleInput::inputStringToLong);
    }

    @DisplayName("createVoucherRequest 테스트 - 값을 받아 VoucherCreateRequest 를 만든다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 100", "percent, 50"},delimiterString = ",")
    void createRequestTest(String voucherTypeInput, Long discountAmount) {
        //given
        InputStream inputStream = createInputStreamSequence(voucherTypeInput, discountAmount.toString());
        System.setIn(inputStream);
        consoleInput = new ConsoleInput(new BufferedReader(new InputStreamReader(inputStream)));

        ConsoleOutput mockConsoleOutput = mock(ConsoleOutput.class);

        //when
        VoucherCreateRequest voucherCreateRequest = consoleInput.getVoucherCreateRequest(
            mockConsoleOutput);

        //then
        assertEquals(VoucherType.of(voucherTypeInput), voucherCreateRequest.getVoucherType());
        assertEquals(discountAmount, voucherCreateRequest.getDiscountAmount());
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