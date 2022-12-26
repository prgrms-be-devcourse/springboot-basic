package org.prgrms.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    private static Stream<Arguments> correctVoucherDiscountAmountArguments() {
        return Stream.of(
                Arguments.of("1", 0),
                Arguments.of("1", 50000),
                Arguments.of("1", 1000000),
                Arguments.of("2", 0),
                Arguments.of("2", 50),
                Arguments.of("2", 100)
        );
    }

    private static Stream<Arguments> incorrectVoucherDiscountAmountArguments() {
        return Stream.of(
                Arguments.of("1", -1),
                Arguments.of("1", 1000001),
                Arguments.of("2", -1),
                Arguments.of("2", 101)
        );
    }

    @Test
    @DisplayName("바우처 정상 생성 확인")
    void createVoucher() {
        // given
        UUID fixedAmountVoucherId = UUID.randomUUID();
        int fixedAmountVoucherDiscountAmount = 100;
        String fixedAmountVoucherTypeInput = "1";
        UUID fixedCustomerId = UUID.randomUUID();

        UUID percentDiscountVoucherId = UUID.randomUUID();
        int percentDiscountVoucherDiscountAmount = 50;
        String percentDiscountVoucherTypeInput = "2";
        UUID percentCustomerId = UUID.randomUUID();

        // when
        Voucher fixedAmountVoucher = VoucherType.createVoucher(
                fixedAmountVoucherId, fixedAmountVoucherTypeInput, fixedAmountVoucherDiscountAmount, fixedCustomerId
        );
        Voucher percentDiscountVoucher = VoucherType.createVoucher(
                percentDiscountVoucherId, percentDiscountVoucherTypeInput, percentDiscountVoucherDiscountAmount, percentCustomerId
        );

        // then
        assertThat(fixedAmountVoucher)
                .hasFieldOrPropertyWithValue("voucherId", fixedAmountVoucherId)
                .hasFieldOrPropertyWithValue("discountAmount", fixedAmountVoucherDiscountAmount)
                .hasFieldOrPropertyWithValue("voucherType", VoucherType.FIXED_AMOUNT);

        assertThat(percentDiscountVoucher)
                .hasFieldOrPropertyWithValue("voucherId", percentDiscountVoucherId)
                .hasFieldOrPropertyWithValue("discountAmount", percentDiscountVoucherDiscountAmount)
                .hasFieldOrPropertyWithValue("voucherType", VoucherType.PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("바우처 생성 실패")
    void failCreateVoucher() {

        // given
        UUID voucherId = UUID.randomUUID();
        int discountAmount = 100;
        String voucherTypeInput = "3";
        UUID customerId = UUID.randomUUID();
        // when, then
        assertThrows(InCorrectVoucherTypeException.class, () -> VoucherType.createVoucher(voucherId, voucherTypeInput, discountAmount, customerId));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "FIXED_AMOUNT", "PERCENT_DISCOUNT"})
    @DisplayName("입력값이 올바른 바우처타입인지 확인")
    void isCorrectVoucherType(String voucherType) {

        // when
        boolean result = VoucherType.isCorrectVoucherType(voucherType);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("입력값이 올바르지 않은 바우처타입일 경우")
    void isIncorrectVoucherType() {

        // given
        String incorrectVoucherType = "3";

        // when
        boolean result = VoucherType.isCorrectVoucherType(incorrectVoucherType);

        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("correctVoucherDiscountAmountArguments")
    @DisplayName("바우처 타입에 따른 정상 범위의 할인률인지 확인")
    void correctVoucherDiscountAmount(String voucherType, int discountAmount) {

        // when
        boolean result = VoucherType.isCorrectDiscountAmount(voucherType, discountAmount);

        // then
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("incorrectVoucherDiscountAmountArguments")
    @DisplayName("바우처 타입에 따른 비정상 범위의 할인률일 경우")
    void incorrectVoucherDiscountAmount(String voucherType, int discountAmount) {

        // when
        boolean result = VoucherType.isCorrectDiscountAmount(voucherType, discountAmount);

        // then
        assertFalse(result);
    }

}