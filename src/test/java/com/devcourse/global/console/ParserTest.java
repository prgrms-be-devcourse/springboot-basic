package com.devcourse.global.console;

import com.devcourse.global.Command;
import com.devcourse.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {
    private final IoParser parser = new IoParser();

    @Nested
    class parsingEnumTest {
        @Test
        @DisplayName("Command에 없는 값을 파싱하려고 하면 예외가 발생한다.")
        void parsingCommandFailTest() {
            // given
            String input = "im not command";

            // when, then
            assertThatThrownBy(() -> parser.parseCommand(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("Command에 지원하는 문자열을 파싱하면 정상적으로 반환된다.")
        @EnumSource(Command.class)
        void parsingCommandSuccessTest(Command command) {
            // given

            // when
            Command parsedCommand = parser.parseCommand(command.name());

            // then
            assertThat(parsedCommand).isEqualTo(command);
        }

        @Test
        @DisplayName("Voucher Type에 없는 값을 파싱하려고 하면 예외가 발생한다.")
        void parsingVoucherTypeFailTest() {
            // given
            String input = "im not type";

            // when, then
            assertThatThrownBy(() -> parser.parseCommand(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("Voucher Type에 지원하는 문자열을 파싱하면 정상적으로 반환된다.")
        @EnumSource(Voucher.Type.class)
        void parsingVoucherTypeSuccessTest(Voucher.Type type) {
            // given

            // when
            Voucher.Type parsedType = parser.parseVoucherType(type.name());

            // then
            assertThat(parsedType).isEqualTo(type);
        }
    }

    @Nested
    class parsingExpirationDateTest {
        @Test
        @DisplayName("타임 포멧에 맞는 값이 들어오면 정상적으로 파싱된다.")
        void parsingExpiredAtSuccessTest() {
            // given
            LocalDate now = LocalDate.now();
            String input = now.toString();
            LocalDateTime expect = LocalDateTime.of(now, LocalTime.MIDNIGHT);

            // when
            LocalDateTime expiredAt = parser.parseExpiration(input);

            // then
            assertThat(expiredAt).isEqualTo(expect);
        }

        @ParameterizedTest
        @DisplayName("yyyy-mm-dd 포멧과 다른 값이 들어오면 예외를 던진다.")
        @ValueSource(strings = {"2023-1-1", "23-01-01", "hello world!"})
        void parsingExpiredAtFailTest(String input) {
            // given

            // when, then
            assertThatThrownBy(() -> parser.parseExpiration(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
