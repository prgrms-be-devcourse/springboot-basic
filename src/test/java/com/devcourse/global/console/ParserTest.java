package com.devcourse.global.console;

import com.devcourse.global.Command;
import com.devcourse.voucher.domain.Voucher.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.devcourse.voucher.domain.Voucher.Type.FIXED;
import static com.devcourse.voucher.domain.Voucher.Type.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {
    private final IoParser parser = new IoParser();

    @Nested
    @DisplayName("enum 파싱 테스트")
    class parsingEnumTest {
        @Test
        @DisplayName("Command에 없는 값을 파싱하려고 하면 예외가 발생한다.")
        void parsingCommand_Fail_ByNotSupportCommand() {
            // given
            String input = "im not command";

            // when, then
            assertThatThrownBy(() -> parser.parseCommand(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("Command에 지원하는 문자열을 파싱하면 정상적으로 반환된다.")
        @EnumSource(Command.class)
        void parsingCommand_SuccessTest(Command command) {
            // given

            // when
            Command parsedCommand = parser.parseCommand(command.name());

            // then
            assertThat(parsedCommand).isEqualTo(command);
        }

        @Test
        @DisplayName("Voucher Type에 없는 값을 파싱하려고 하면 예외가 발생한다.")
        void parsingVoucherType_Fail_ByNotSupportType() {
            // given
            String input = "im not type";

            // when, then
            assertThatThrownBy(() -> parser.parseCommand(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("Voucher Type에 지원하는 문자열을 파싱하면 정상적으로 반환된다.")
        @EnumSource(Type.class)
        void parsingVoucherType_SuccessTest(Type type) {
            // given

            // when
            Type parsedType = parser.parseVoucherType(type.name());

            // then
            assertThat(parsedType).isEqualTo(type);
        }
    }

    @Nested
    @DisplayName("만료 일자 파싱 테스트")
    class parsingExpirationDateTest {
        @Test
        @DisplayName("타임 포멧에 맞는 값이 들어오면 정상적으로 파싱된다.")
        void toValidExpiredAt_Success() {
            // given
            LocalDate now = LocalDate.now().plusDays(1);
            String input = now.toString();
            LocalDateTime expect = LocalDateTime.of(now, LocalTime.MIDNIGHT);

            // when
            LocalDateTime expiredAt = parser.toValidExpiredAt(input);

            // then
            assertThat(expiredAt).isEqualTo(expect);
        }

        @ParameterizedTest
        @DisplayName("yyyy-mm-dd 포멧과 다른 값이 들어오면 예외를 던진다.")
        @ValueSource(strings = {"2023-1-1", "23-01-01", "hello world!"})
        void toValidExpiredAt_Fail_ByWrongFormat(String input) {
            // given

            // when, then
            assertThatThrownBy(() -> parser.toValidExpiredAt(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("현재보다 과거의 만료일이 입력되면 IllegalArgumentException을 던진다.")
        void toValidExpiredAt_Fail_ByPastThanNow() {
            // given
            String input = "2022-01-01";

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> parser.toValidExpiredAt(input));
        }
    }

    @Nested
    @DisplayName("사용 가능한 입력값 파싱 테스트")
    class parsingValidDiscountTest {
        @ParameterizedTest
        @DisplayName("")
        @CsvSource({
                "FIXED, 1000, 1000",
                "PERCENT, 50, 50"
        })
        void toValidDiscountByType_Success(Type type, String discount, int result) {
            // given

            // when
            int validDiscount = parser.toValidDiscountByType(type, discount);

            // then
            assertThat(validDiscount).isEqualTo(result);
        }

        @ParameterizedTest
        @DisplayName("문자를 입력하면 IllegalArgumentException을 던져야 한다.")
        @ValueSource(strings = {"hello", "john", "world"})
        void toValidDiscountByType_Fail_ByCharacterInput(String input) {
            // given

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> parser.toValidDiscountByType(FIXED, input));
        }

        @Test
        @DisplayName("퍼센트 단위 (0~100)을 넘어가면 IllegalArgumentException을 던져야 한다.")
        void toValidDiscountByType_Fail_ByOutOfRate() {
            // given
            String invalidInput = "101";

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> parser.toValidDiscountByType(PERCENT, invalidInput));
        }

        @ParameterizedTest
        @DisplayName("0이나 음수가 들어오면 ")
        @ValueSource(strings = {"0", "-1"})
        void toValidDiscountByType_Fail_ByNegativeAndZero(String input) {
            // given

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> parser.toValidDiscountByType(FIXED, input));
        }
    }
}
