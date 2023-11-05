package com.prgrms.vouchermanagement.core.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    @DisplayName("VoucherType에 해당하지 않는 type이 들어오면 예외가 발생한다.")
    @Test
    void validateVoucherTypeTest1() {
        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> new Voucher("sujin", 100, "WRONG_VOUCHER_TYPE"));
    }

    @DisplayName("VoucherType에 해당하는 type인 경우에는 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"fixed", "rate"})
    void validateVoucherTypeTest2(String voucherType) {
        // given

        // when

        // then
        assertDoesNotThrow(() -> new Voucher("sujin", 100, voucherType));
    }

    @DisplayName("고정 할인 금액이 0보다 작으면 예외가 발생한다.")
    @Test
    void validateAmountByVoucherType() {
        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> new Voucher("sujin", -1, "fixed"));
    }

    @DisplayName("고정 할인 금액이 0 이상이면 예외가 발생하지 않는다.")
    @Test
    void validateAmountByVoucherType2() {
        // given

        // when

        // then
        assertDoesNotThrow(() -> new Voucher("sujin", 0, "fixed"));
    }

    @DisplayName("할인율이 0보다 작거나 100보다 크면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    void validateAmountByVoucherType3(long amount) {
        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> new Voucher("sujin", amount, "rate"));
    }

    @DisplayName("할인율이 0 이상 100 이하이면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 100})
    void validateAmountByVoucherType4(long amount) {
        // given

        // when

        // then
        assertDoesNotThrow(() -> new Voucher("sujin", amount, "rate"));
    }
}