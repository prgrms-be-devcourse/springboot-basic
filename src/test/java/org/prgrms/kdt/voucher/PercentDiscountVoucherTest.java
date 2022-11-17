package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private Voucher createVoucher(UUID uuid, double percent){
        return new PercentDiscountVoucher(uuid, percent);
    }

    @DisplayName("할인 정도는 제한보다 작을 수 없다.")
    @ParameterizedTest
    @ValueSource(doubles = {-10, 0.1, 0})
    void minLimitAmountTest(double percent) {
        assertThrows(AmountException.class, () -> {
            createVoucher(UUID.randomUUID(), percent);
        });
    }

    @DisplayName("할인 정도는 제한보다 클 수 없다.")
    @ParameterizedTest
    @ValueSource(doubles = {120, 200, 101, 100.1})
    void maxLimitAmountTest(double percent) {
        assertThrows(AmountException.class, () -> {
            createVoucher(UUID.randomUUID(), percent);
        });
    }

    @DisplayName("주어진 금액만큼 할인이 되어야 한다.")
    @ParameterizedTest
    @CsvSource({"10, 10000, 9000", "40, 10000, 6000", "70, 10000, 3000"})
    void discountAmountTest(double percent, double beforeDiscount, double expected) {
        // given
        Voucher voucher = createVoucher(UUID.randomUUID(), percent);
        // when
        double afterDiscount = voucher.discount(beforeDiscount);
        // then
        assertEquals(expected, afterDiscount);
    }
}