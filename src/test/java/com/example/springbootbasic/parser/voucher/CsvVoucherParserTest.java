package com.example.springbootbasic.parser.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CsvVoucherParserTest {

    private final CsvVoucherParser voucherParser = new CsvVoucherParser();

    @ParameterizedTest(name = "[{index}] csv = {0}, voucherType = {1}")
    @MethodSource("whenParsingVoucherCsvThenSuccessDummy")
    @DisplayName("csv를 파싱하여 바우처 타입 확인 성공")
    void whenParsingVoucherCsvThenSuccessTest(String voucherCsv, VoucherType voucherType) {
        Voucher voucher = voucherParser.toVoucherFrom(voucherCsv);
        assertThat(voucher.getVoucherType(), is(voucherType));
    }

    @ParameterizedTest(name = "[{index}] csv = {0}")
    @MethodSource("whenParsingVoucherWrongThenExceptionDummy")
    @DisplayName("csv 파싱 중 실패 예외처리")
    void whenParsingVoucherWrongThenExceptionTest(String voucherCsv) {
        assertThrowsExactly(IllegalArgumentException.class, () -> voucherParser.toVoucherFrom(voucherCsv));
    }

    static Stream<Arguments> whenParsingVoucherCsvThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments("1 fixed 1", FIXED_AMOUNT),
                Arguments.arguments("2 fixed 100", FIXED_AMOUNT),
                Arguments.arguments("3 fixed 1000", FIXED_AMOUNT),
                Arguments.arguments("4 fixed 10000", FIXED_AMOUNT),
                Arguments.arguments("5 fixed 50000", FIXED_AMOUNT),
                Arguments.arguments("1 percent 10", PERCENT_DISCOUNT),
                Arguments.arguments("2 percent 20", PERCENT_DISCOUNT),
                Arguments.arguments("3 percent 30", PERCENT_DISCOUNT),
                Arguments.arguments("4 percent 53", PERCENT_DISCOUNT),
                Arguments.arguments("5 percent 100", PERCENT_DISCOUNT)
        );
    }

    static Stream<Arguments> whenParsingVoucherWrongThenExceptionDummy() {
        return Stream.of(
                Arguments.arguments("1a fixed 1"),
                Arguments.arguments("a fixed 100"),
                Arguments.arguments("3 fi244xed 1000"),
                Arguments.arguments("4 fixed 1fa0000"),
                Arguments.arguments("5g fixe3d 5fs0000"),
                Arguments.arguments("gf pe23rcent 10"),
                Arguments.arguments("2 perce3nt 2d0"),
                Arguments.arguments("3 perce3nt 30"),
                Arguments.arguments("4 percent 5f3"),
                Arguments.arguments("a percent 10f0")
        );
    }
}