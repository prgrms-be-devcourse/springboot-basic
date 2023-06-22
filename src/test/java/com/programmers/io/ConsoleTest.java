package com.programmers.io;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsoleTest {

    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;

    private final Console console = new Console();

    @BeforeEach
    public void before() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void after() {
        System.setOut(printStream);
    }

    @DisplayName("메뉴를 출력한다")
    @Test
    void printMenu() {
        //given
        //when
        console.printMenu();

        //then
        assertThat(outputStream.toString()).contains(MENU_MESSAGE);
    }

    @DisplayName("값을 입력받는다")
    @ValueSource(strings = {"1", "2", "3"})
    @ParameterizedTest
    void readInput(String input) {
        //given
        //when
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String result = console.readInput();

        //then
        assertThat(result).contains(input);
    }
}