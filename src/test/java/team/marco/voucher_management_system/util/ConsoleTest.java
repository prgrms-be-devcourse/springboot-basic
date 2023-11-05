package team.marco.voucher_management_system.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import com.github.javafaker.Faker;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ConsoleTest {
    private final static Faker faker = new Faker();
    private final static String EOF = "\u001a";

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void cleanup() {
        Console.close();
    }

    @Nested
    @DisplayName("readString() 테스트")
    class TestReadString {
        @Test
        @DisplayName("콘솔 입력을 읽을 수 있다.")
        void normal() {
            // given
            String input = faker.address().country();

            setStdin(input);

            // when
            String readString = Console.readString();

            // then
            assertThat(readString).isEqualTo(input);
        }

        @Test
        @DisplayName("EOF 발생 시 예외를 발생한다.")
        void EOF() {
            // given
            setStdin(EOF);

            Console.readString(); // skip EOF character

            // when
            ThrowingCallable target = Console::readString;

            // then
            assertThatException().isThrownBy(target)
                    .isInstanceOf(UncheckedIOException.class)
                    .withMessageContaining("입력 과정에서 오류가 발생했습니다.");
        }
    }

    @Nested
    @DisplayName("readInt() 테스트")
    class TestReadInt {
        @Test
        @DisplayName("콘솔 입력을 숫자로 변환할 수 있다.")
        void normal() {
            // given
            String input = faker.number().digit();

            setStdin(input);

            // when
            int readInt = Console.readInt();

            // then
            assertThat(readInt).isEqualTo(Integer.parseInt(input));
        }

        @Test
        @DisplayName("숫자로 변환할 수 없을 시 예외를 발생한다.")
        void numberFormatException() {
            // given
            String input = faker.name().firstName();

            setStdin(input);

            // when
            ThrowingCallable target = Console::readInt;

            // then
            assertThatException().isThrownBy(target)
                    .isInstanceOf(NumberFormatException.class);
        }
    }

    @Test
    @DisplayName("사용자 입력을 콘솔로 출력할 수 있다.")
    void testPrint() {
        // given
        String userOutput = faker.name().firstName();

        // when
        Console.print(userOutput);

        // then
        String stdout = getStdout();

        assertThat(stdout).startsWith(userOutput);
    }

    private void setStdin(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private String getStdout() {
        return outputStreamCaptor.toString();
    }
}
