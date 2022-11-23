package com.example.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherFactoryTest {

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("whenGenerateVoucherThenSuccessDummy")
    @DisplayName("바우처 타입과 할인 금액을 이용한 바우처 생성 성공")
    void whenGenerateVoucherThenSuccessTest(Long discountValue, VoucherType voucherType) {
        Voucher generateVoucher = VoucherFactory.of(discountValue, voucherType);
        assertThat(generateVoucher.getVoucherType(), is(voucherType));
    }

    static Stream<Arguments> whenGenerateVoucherThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments(1L, FIXED_AMOUNT),
                Arguments.arguments(10L, FIXED_AMOUNT),
                Arguments.arguments(100L, FIXED_AMOUNT),
                Arguments.arguments(10000L, FIXED_AMOUNT),
                Arguments.arguments(49999L, FIXED_AMOUNT),
                Arguments.arguments(50000L, FIXED_AMOUNT),
                Arguments.arguments(10L, PERCENT_DISCOUNT),
                Arguments.arguments(99L, PERCENT_DISCOUNT),
                Arguments.arguments(100L, PERCENT_DISCOUNT)
        );
    }
}