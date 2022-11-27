package com.example.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherTypeTest {

    @ParameterizedTest(name = "[{index}] inputVoucherType = {0}, voucherType = {1}")
    @MethodSource("whenFindVoucherTypeThenSuccessDummy")
    @DisplayName("바우처 타입 정확한 입력값으로 검색 성공")
    void whenFindVoucherTypeThenSuccessTest(String inputVoucherType, VoucherType voucherType) {
        VoucherType findVoucherType = VoucherType.of(inputVoucherType);
        assertThat(findVoucherType, is(voucherType));
    }

    @ParameterizedTest(name = "[{index}] inputVoucherType = {0}")
    @MethodSource("whenFindVoucherTypeWrongThenExceptionDummy")
    @DisplayName("바우처 타입 잘못된 입력값으로 인한 검색 예외 처리")
    void whenFindVoucherTypeWrongThenExceptionTest(String inputVoucherType) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> VoucherType.of(inputVoucherType));
    }

    static Stream<Arguments> whenFindVoucherTypeThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments("fixed", FIXED_AMOUNT),
                Arguments.arguments("percent", PERCENT_DISCOUNT)
        );
    }

    static Stream<Arguments> whenFindVoucherTypeWrongThenExceptionDummy() {
        return Stream.of(
                Arguments.arguments("fixxeed"),
                Arguments.arguments("fixeed"),
                Arguments.arguments("fixeddd"),
                Arguments.arguments("fixedd"),
                Arguments.arguments("fixe"),
                Arguments.arguments("fix"),
                Arguments.arguments("fi"),
                Arguments.arguments("f"),
                Arguments.arguments("percenttt"),
                Arguments.arguments("percentt"),
                Arguments.arguments("percennt"),
                Arguments.arguments("percenn"),
                Arguments.arguments("percen"),
                Arguments.arguments("perce"),
                Arguments.arguments("perc"),
                Arguments.arguments("per"),
                Arguments.arguments("pe"),
                Arguments.arguments("p")
        );
    }
}