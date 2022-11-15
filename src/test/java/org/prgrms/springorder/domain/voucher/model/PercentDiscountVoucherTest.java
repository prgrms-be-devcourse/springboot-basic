package org.prgrms.springorder.domain.voucher.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;

class PercentDiscountVoucherTest {

    @DisplayName("생성 테스트 - amount 가 0 이상  100 이하 이면 객체 생성에 성공한다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 2L, 3L, 10L, 100L, 99L})
    void createSuccessTest(long amount) {
        assertDoesNotThrow(() -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("생성 테스트 - amount 가 0 보다 작으면 객체 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(longs = {-1L, -2L, -3L, -10L, -100L, -1000L})
    void createFailLessThen0Test(long amount) {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("생성 테스트 - amount 가 100 보다 크면 객체 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(longs = {103L, 101L, 102L, 999L, 1000L, 919293L})
    void createFailGraterThen100Test(long amount) {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("discount 테스트 - beforeDiscount 에서 amount * 100 만큼 할인된 값이 리턴된다.")
    @ParameterizedTest
    @CsvSource(value = {"100,10,90", "100,50,50", "1000,90,100", "10000,5,9500"}, delimiterString = ",")
    void discountTest(long beforeDiscount, long amount, long expectedAfterDiscount) {
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);

        //when
        long afterDiscount = percentDiscountVoucher.discount(beforeDiscount);

        //then
        assertEquals(expectedAfterDiscount, afterDiscount);
    }

}