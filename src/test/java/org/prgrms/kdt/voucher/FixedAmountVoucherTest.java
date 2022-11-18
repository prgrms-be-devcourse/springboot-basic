package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {
    @DisplayName("할인 정도는 제한보다 작을 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {10, 0, -10})
    void minLimitAmountTest(int amount) {
        assertThrows(AmountException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID().toString(), amount));
    }

    @DisplayName("할인 정도는 제한보다 클 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {100000, 15000})
    void maxLimitAmountTest(int amount) {
        assertThrows(AmountException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID().toString(), amount));
    }

    @DisplayName("주어진 금액만큼 할인이 되어야 한다.")
    @ParameterizedTest
    @CsvSource({"30, 10000", "300, 10000", "3000, 10000"})
    void discountAmountTest(int amount, int beforeDiscount) {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID().toString(), amount);
        // when
        double afterDiscount = voucher.discount(beforeDiscount);
        // then
        assertEquals(beforeDiscount - amount, afterDiscount);
    }
}