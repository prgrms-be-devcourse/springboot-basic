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
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @ParameterizedTest
    @DisplayName("비율값 바우처 할인 적용된 결과 반환 테스트")
    @MethodSource("provideVouchers")
    void discountedPriceTest(long originalPrice, PercentDiscountVoucher voucher) {
        var result = voucher.discountedPrice(originalPrice);
        assertThat(result, is(greaterThanOrEqualTo(0.0)));
    }

    @Test
    @DisplayName("비율값 바우처 할인 잘못 적용되었을때 예외 던지기")
    void discountedPriceExceptionTest() {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"));
        assertThrows(InvalidDataException.class, () -> voucher.discountedPrice(-1L));
    }

    @ParameterizedTest
    @DisplayName("비율값 바우처 문자열 반환 테스트")
    @MethodSource("provideVouchers")
    void testToString(long originalPrice, PercentDiscountVoucher voucher) {
        var expected = MessageFormat.format("{0}:{1},{2},{3}", voucher.getVoucherType().name(), voucher.getVoucherId(), voucher.voucherType.getTypeString(), voucher.getDiscountValue().getValue());
        var result = voucher.toString();
        assertEquals(expected, result);
    }

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(100, new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"))),
                Arguments.of(100, new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "100"))),
                Arguments.of(100, new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")))
        );
    }

}