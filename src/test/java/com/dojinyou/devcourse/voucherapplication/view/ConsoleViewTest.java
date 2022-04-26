package com.dojinyou.devcourse.voucherapplication.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsoleView.class)
class ConsoleViewTest {
    private static final String ERROR_MESSAGE_ABOUT_REFLEXTION = "reflextion 과정에서 에러가 발생하였습니다.\n";

    @Autowired
    ConsoleView consoleView;

    public InputStream generateUserInput(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    @Nested
    @DisplayName("InputView의 getUserInput Method 테스트")
    class getUserInputTest {
        @ParameterizedTest
        @ValueSource(strings = {" ", "\n"})
        @DisplayName("입력값이 없을 때,  IllegalArgumentException을 발생시킨다.")
        void emptyInputTest(String emptyInput) {
            // 테스트 환경 설정

            InputStream emptyInputStream = generateUserInput(emptyInput);
            System.setIn(emptyInputStream);
            Scanner testScanner = new Scanner(System.in);
            try {
                Field scannerField = consoleView.getClass().getDeclaredField("scanner");
                setFinalStatic(scannerField, testScanner);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
            }

            // 테스트 메소드 호출 및 반환 타입 저장
            Throwable thrown = catchThrowable(() -> consoleView.getUserInput());


            // 반환에 대한 검증
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"create", "adfafadsf", "exit"})
        @DisplayName("정상적인 입력값이 들어온다면, 해당 입력값을 전달한다.")
        void normalInputTest(String normalInput) {
            // 테스트 환경 설정

            InputStream emptyInputStream = generateUserInput(normalInput);
            System.setIn(emptyInputStream);
            Scanner testScanner = new Scanner(System.in);
            try {
                Field scannerField = consoleView.getClass().getDeclaredField("scanner");
                setFinalStatic(scannerField, testScanner);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
            }

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
        @ValueSource(strings = {"", "\n", " "})
        @DisplayName("빈 출력값이 들어온다면, IllegalArgumentException을 발생시킨다.")
        void emptyOrNullInput(String emptyOutput) {
            Throwable thrown = catchThrowable(() -> consoleView.display(emptyOutput));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"testOutput", "adfafadsf"})
        @DisplayName("정상적인 출력값이 들어온다면, 해당 출력값을 출력한다.")
        void normalInputTest(String normalOutput) {
            // 테스트 환경 설정
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            // 테스트 메소드 호출 및 반환 타입 저장
            consoleView.display(normalOutput);

            // 반환에 대한 검증
            assertThat(outputStream.toString()).isEqualTo(normalOutput + "\n");
        }
    }

    static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
        }

        field.set(null, newValue);
    }
}