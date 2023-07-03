package com.devcourse.springbootbasic.application.domain.voucher;

import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTest {

    static Stream<Arguments> providePercentVouchers() {
        return Stream.of(
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"))),
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "100"))),
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")))
        );
    }

    static Stream<Arguments> provideFixedVouchers() {
        return Stream.of(
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "10"))),
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "0")))
        );
    }

    static Stream<Arguments> provideInvalidFixedVouchers() {
        return Stream.of(
                Arguments.of(100L, new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "101"))),
                Arguments.of(-1L, new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "0")))
        );
    }

    @ParameterizedTest
    @DisplayName("비율값 바우처 할인 적용된 결과 반환 테스트")
    @MethodSource("providePercentVouchers")
    void discountedPricePercentTest(long originalPrice, Voucher voucher) {
        var result = voucher.discountedPrice(originalPrice);
        assertThat(result, is(greaterThanOrEqualTo(0.0)));
    }

    @Test
    @DisplayName("비율값 바우처 할인 잘못 적용되었을때 예외 던지기")
    void discountedPricePercentExceptionTest() {
        var voucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"));
        assertThrows(InvalidDataException.class, () -> voucher.discountedPrice(-1L));
    }

    @ParameterizedTest
    @DisplayName("비율값 바우처 문자열 반환 테스트")
    @MethodSource("providePercentVouchers")
    void testPercentToString(Voucher voucher) {
        var expected = MessageFormat.format("{0}:{1},{2},{3}", voucher.getVoucherType().name(), voucher.getVoucherId(), voucher.getVoucherType().getTypeString(), voucher.getDiscountValue().getValue());
        var result = voucher.toString();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 적용된 결과 반환 테스트")
    @MethodSource("provideFixedVouchers")
    void discountedPriceFixedTest(long originalPrice, Voucher voucher) {
        var result = voucher.discountedPrice(originalPrice);
        assertThat(result, is(greaterThanOrEqualTo(0.0)));
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 잘못 적용되었을때 예외 던지기")
    @MethodSource("provideInvalidFixedVouchers")
    void discountedPriceFixedExceptionTest(long originalPrice, Voucher voucher) {
        assertThrows(InvalidDataException.class, () -> voucher.discountedPrice(originalPrice));
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 문자열 반환 테스트")
    @MethodSource("provideFixedVouchers")
    void testFixedToString(Voucher voucher) {
        var expected = MessageFormat.format("{0}:{1},{2},{3}", voucher.getVoucherType().name(), voucher.getVoucherId(), voucher.getVoucherType().getTypeString(), voucher.getDiscountValue().getValue());
        var result = voucher.toString();
        assertEquals(expected, result);
    }

}