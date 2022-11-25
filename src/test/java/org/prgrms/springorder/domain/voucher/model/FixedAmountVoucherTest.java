package org.prgrms.springorder.domain.voucher.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;

class FixedAmountVoucherTest {

    @DisplayName("생성 테스트 - amount 가 0 이상이면 객체 생성에 성공한다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 2L, 3L, 10L, 100L, 1000L})
    void createSuccessTest(long amount) {

        assertDoesNotThrow(() -> new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("생성 테스트 - amount 가 0 보다 작으면 객체 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(longs = {-1L, -2L, -3L, -10L, -100L, -1000L})
    void createFailTest(long amount) {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    @DisplayName("discount 테스트 - beforeDiscount 에서 amount 가격을 뺀 값이 리턴된다.")
    @ParameterizedTest
    @CsvSource(value = {"10,10,0", "100,50,50", "1000,900,100", "1000,1000,0"}, delimiterString = ",")
    void discountTest(long beforeDiscount, long amount, long expectedAfterDiscount) {

        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);

        //when
        long afterDiscount = fixedAmountVoucher.discount(beforeDiscount);

        //then
        assertEquals(expectedAfterDiscount, afterDiscount);
    }

    @DisplayName("discount 테스트 - beforeDiscount 에서 amount 가격을 뺀 값이 0보다 작으면 0이 리턴된다.")
    @ParameterizedTest
    @CsvSource(value = {"10,100", "100,500", "1000,1001", "1000,1000"}, delimiterString = ",")
    void discountTestReturn0(long beforeDiscount, long amount) {

        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);

        //when
        long afterDiscount = fixedAmountVoucher.discount(beforeDiscount);

        //then
        assertEquals(0, afterDiscount);
    }

}
