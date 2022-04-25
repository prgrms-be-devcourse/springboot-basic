package com.example.voucher.io;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Output 인터페이스의")
public class OutputTest {
    private final Output output = new ConsoleWriter();

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class printMessage메서드는 {

        private OutputStream out;

        @BeforeEach
        void 테스트를_위한_설정() {
            out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

        }
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 지원하는_타입의_메시지가_넘어온다면 {

            @Test
            @DisplayName("응답_문자열을_생성하여_출력한다")
            void 응답_문자열을_생성하여_출력한다() {
                output.printMessage("지원하는 타입의 메시지입니다.");

                String result = out.toString();
                assertThat(result).isNotEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 지원하지_않는_타입의_메시지가_넘어온다면 {

            @Test
            @DisplayName("처리하지 않고 리턴한다")
            void 처리하지_않고_리턴한다() {
                output.printMessage("지원하지 않는 타입의 메시지입니다.");

                String result = out.toString();
                assertThat(result).isEmpty();

            }
        }
    }
}

