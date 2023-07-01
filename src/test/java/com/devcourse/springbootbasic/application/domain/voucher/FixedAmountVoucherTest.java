package com.devcourse.springbootbasic.application.domain.voucher;

import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 적용된 결과 반환 테스트")
    @MethodSource("provideVouchers")
    void discountedPriceTest(long originalPrice, FixedAmountVoucher voucher) {
        var result = voucher.discountedPrice(originalPrice);
        assertThat(result, is(greaterThanOrEqualTo(0.0)));
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 잘못 적용되었을때 예외 던지기")
    @MethodSource("provideInvalidVouchers")
    void discountedPriceExceptionTest(long originalPrice, FixedAmountVoucher voucher) {
        assertThrows(InvalidDataException.class, () -> voucher.discountedPrice(originalPrice));
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 문자열 반환 테스트")
    @MethodSource("provideVouchers")
    void testToString(long originalPrice, FixedAmountVoucher voucher) {
        var expected = MessageFormat.format("{0}:{1},{2},{3}", voucher.getVoucherType().name(), voucher.getVoucherId(), voucher.voucherType.getTypeString(), voucher.getDiscountValue().getValue());
        var result = voucher.toString();
        assertEquals(expected, result);
    }

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(100, new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "10"))),
                Arguments.of(100, new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(100, new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "0")))
        );
    }

    static Stream<Arguments> provideInvalidVouchers() {
        return Stream.of(
                Arguments.of(100, new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "101"))),
                Arguments.of(-1, new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "0")))
        );
    }

}