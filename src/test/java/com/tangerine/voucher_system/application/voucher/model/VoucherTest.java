package com.tangerine.voucher_system.application.voucher.model;

import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VoucherTest {

    static Stream<Arguments> providePercentVouchers() {
        return Stream.of(
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"), LocalDateTime.now(), Optional.of(UUID.randomUUID()))),
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "100"), LocalDateTime.now(), Optional.of(UUID.randomUUID()))),
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"), LocalDateTime.now(), Optional.of(UUID.randomUUID())))
        );
    }

    static Stream<Arguments> provideFixedVouchers() {
        return Stream.of(
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "10"), LocalDateTime.now(), Optional.empty())),
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), LocalDateTime.now(), Optional.empty())),
                Arguments.of(new Price(100), new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "0"), LocalDateTime.now(), Optional.empty()))
        );
    }

    static Stream<Arguments> provideMakeWrongFixedVouchers() {
        return Stream.of(
                Arguments.of(new Price(2), new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "10"), LocalDateTime.now(), Optional.of(UUID.randomUUID()))),
                Arguments.of(new Price(4), new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), LocalDateTime.now(), Optional.empty()))
        );
    }

    @ParameterizedTest
    @DisplayName("비율값 바우처 할인 적용된 결과 반환하면 성공한다.")
    @MethodSource("providePercentVouchers")
    void applyVoucher_ParamPercentDiscountValue_ReturnPrice(Price originalPrice, Voucher voucher) {
        Price result = voucher.applyVoucher(originalPrice);

        assertThat(result.getValue()).isGreaterThanOrEqualTo(0.0);
    }

    @ParameterizedTest
    @DisplayName("고객 아이디가 부재하는 바우처 문자열 반환하면 성공한다.")
    @MethodSource("provideFixedVouchers")
    void toString_PercentVoucher_ReturnVoucherString(Price originalPrice, Voucher voucher) {
        String expected = MessageFormat.format(
                "Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}, createdAt={3}'}'",
                voucher.getVoucherId().toString(),
                voucher.getVoucherType().toString(),
                voucher.getDiscountValue().getValue(),
                voucher.getCreatedAt().toString());

        String result = voucher.toString();

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 적용된 결과 반환하면 성공한다.")
    @MethodSource("provideFixedVouchers")
    void applyVoucher_ParamFixedDiscountValue_ReturnPrice(Price originalPrice, Voucher voucher) {
        Price result = voucher.applyVoucher(originalPrice);

        assertThat(result.getValue()).isGreaterThanOrEqualTo(0.0);
    }

    @ParameterizedTest
    @DisplayName("고정값 바우처 할인 잘못 적용되었을때 예외 던지고 실패한다.")
    @MethodSource("provideMakeWrongFixedVouchers")
    void applyVoucher_ParamInvalidFixDiscountValue_Exception(Price originalPrice, Voucher voucher) {
        Exception exception = catchException(() -> voucher.applyVoucher(originalPrice));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("고객 아이디가 존재하는 바우처 문자열 반환하면 성공한다.")
    @MethodSource("providePercentVouchers")
    void toString_FixedVoucher_ReturnVoucherString(Price originalPrice, Voucher voucher) {
        String expected = MessageFormat.format(
                "Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}, createdAt={3}, customerId={4}'}'",
                voucher.getVoucherId().toString(),
                voucher.getVoucherType().toString(),
                voucher.getDiscountValue().getValue(),
                voucher.getCreatedAt().toString(),
                voucher.getCustomerId().toString());

        String result = voucher.toString();

        assertEquals(expected, result);
    }

}