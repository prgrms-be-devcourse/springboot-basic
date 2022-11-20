package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @DisplayName("할인 정도는 제한보다 작을 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-10, 0})
    void minLimitAmountTest(int percent) {
        assertThrows(AmountException.class,
                () -> new PercentDiscountVoucher(UUID.randomUUID().toString(), percent));
    }

    @DisplayName("할인 정도는 제한보다 클 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {120, 200, 101})
    void maxLimitAmountTest(int percent) {
        assertThrows(AmountException.class,
                () -> new PercentDiscountVoucher(UUID.randomUUID().toString(), percent));
    }

    @DisplayName("주어진 금액만큼 할인이 되어야 한다.")
    @ParameterizedTest
    @CsvSource({"10, 10000, 9000", "40, 10000, 6000", "70, 10000, 3000"})
    void discountAmountTest(int percent, int beforeDiscount, int expected) {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID().toString(), percent);

        // when
        int afterDiscount = voucher.discount(beforeDiscount);

        // then
        assertEquals(expected, afterDiscount);
    }
}