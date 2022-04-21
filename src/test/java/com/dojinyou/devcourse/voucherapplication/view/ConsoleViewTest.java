package com.dojinyou.devcourse.voucherapplication.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConsoleViewTest {
    private ConsoleView consoleView;

    public InputStream generateUserInput(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    @Nested
    @DisplayName("InputView의 getUserInput Method 테스트")
    class getUserInputTest{
        @ParameterizedTest
        @ValueSource(strings = {"\n", " "})
        @DisplayName("입력값이 없을 때,  IllegalArgumentException을 발생시킨다.")
        void emptyInputTest(String emptyInput) throws NoSuchFieldException, IllegalAccessException {
            // 테스트 환경 설정
            consoleView = new ConsoleView();

            Field errorMessageField = consoleView.getClass().getDeclaredField("EMPTY_INPUT_ERROR_MESSAGE");
            errorMessageField.setAccessible(true);
            String errorMessage = (String) errorMessageField.get(consoleView);

            InputStream emptyInputStream = generateUserInput(emptyInput);
            System.setIn(emptyInputStream);
            Scanner testScanner = new Scanner(System.in);
            Field scannerField = consoleView.getClass().getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(consoleView, testScanner);

            // 테스트 메소드 호출 및 반환 타입 저장
            Throwable thrown = catchThrowable(()->consoleView.getUserInput());

            // 반환에 대한 검증
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            assertThat(thrown.getMessage()).isEqualTo(errorMessage);
        }

        @ParameterizedTest
        @ValueSource(strings = {"create", "adfafadsf", "exit"})
        @DisplayName("정상적인 입력값이 들어온다면, 해당 입력값을 전달한다.")
        void normalInputTest(String normalInput) throws NoSuchFieldException, IllegalAccessException {
            // 테스트 환경 설정
            consoleView = new ConsoleView();
            
            InputStream emptyInputStream = generateUserInput(normalInput);
            System.setIn(emptyInputStream);
            Scanner testScanner = new Scanner(System.in);
            Field scannerField = consoleView.getClass().getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(consoleView, testScanner);
            
            // 테스트 메소드 호출 및 반환 타입 저장
            String userInput = consoleView.getUserInput();
            
            // 반환에 대한 검증
            assertThat(userInput).isEqualTo(normalInput);
        }
    }

    @Nested
    @DisplayName("OutputView의 display Method 테스트")
    class displayTest {
        @ParameterizedTest
        @ValueSource(strings = {"", "\n"," "})
        @DisplayName("빈 출력값이 들어온다면, IllegalArgumentException을 발생시킨다.")
        void emptyOrNullInput(String emptyOutput) throws NoSuchFieldException, IllegalAccessException {
            consoleView = new ConsoleView();

            Field errorMessageField = consoleView.getClass().getDeclaredField("EMPTY_OUTPUT_ERROR_MESSAGE");
            errorMessageField.setAccessible(true);
            String errorMessage = (String) errorMessageField.get(consoleView);

            Throwable thrown = catchThrowable(()->consoleView.disPlay(emptyOutput));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            assertThat(thrown.getMessage()).isEqualTo(errorMessage);
        }

        @ParameterizedTest
        @ValueSource(strings = {"testOutput", "adfafadsf"})
        @DisplayName("정상적인 출력값이 들어온다면, 해당 출력값을 출력한다.")
        void normalInputTest(String normalOutput) {
            // 테스트 환경 설정
            consoleView = new ConsoleView();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            // 테스트 메소드 호출 및 반환 타입 저장
            consoleView.disPlay(normalOutput);

            // 반환에 대한 검증
            assertThat(outputStream.toString()).isEqualTo(normalOutput+"\n");
        }
    }


}