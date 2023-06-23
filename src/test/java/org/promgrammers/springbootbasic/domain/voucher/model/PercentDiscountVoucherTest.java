package org.promgrammers.springbootbasic.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {

    @ParameterizedTest
    @ValueSource(longs = {0L, 5L, 10L})
    @DisplayName("생성 성공 - amount가 0이상 100이하")
    void creationSuccessTest(long amount) throws Exception {

        assertDoesNotThrow(() -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10L, -1000L})
    @DisplayName("생성 실패 - amount가 0이하")
    void creationFailTestUnder0(long amount) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {101L, 110L, 500L})
    @DisplayName("생성 실패 - amount가 100이상")
    void creationFailTestAbove100(long amount) {

        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @ParameterizedTest
    @CsvSource(value = {"100,10,90", "500,5,475", "1000,100,0"})
    @DisplayName("성공 - 원금에 amount*100이 할인된다.")
    void discountTest(long beforeAmount, long amount, long expectedAmount) {

        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        //when
        long afterAmount = percentDiscountVoucher.discount(beforeAmount);
        //then
        assertThat(afterAmount).isEqualTo(expectedAmount);
    }
}