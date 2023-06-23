package org.promgrammers.springbootbasic.domain.voucher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {

    @DisplayName("생성 성공 - amount가 0이상 ")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 4L, 10L, 100L})
    void creationSuccessTest(long amount) throws Exception {

        Assertions.assertDoesNotThrow(() -> new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("생성 실패 - amount가 0이하")
    @ParameterizedTest
    @ValueSource(longs = {-1, -10L, -20L})
    void creationFailTest(long amount) throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("원금에서 할인 금액이 뺀 값 반환")
    @ParameterizedTest
    @CsvSource(value = {"10,10,0", "100,10,90", "2000,1000,1000"})
    void discountTest(long beforeAmount, long amount, long expectedResult) throws Exception {

        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);

        //when
        long afterAmount = fixedAmountVoucher.discount(beforeAmount);

        //then
        assertThat(afterAmount).isEqualTo(expectedResult);
    }

    @DisplayName("원금에서 할인 가격을 뺀 값이 0보다 작으면 0을 반환")
    @ParameterizedTest
    @CsvSource(value = {"10,20,0", "100,200,0", "2000,3000,0"})
    void discountReturn0Test(long beforeAmount, long amount, long expectedResult) throws Exception {

        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);

        //when
        long afterAmount = fixedAmountVoucher.discount(beforeAmount);

        //then
        assertThat(afterAmount).isEqualTo(expectedResult);
    }
}