package com.programmers.vouchermanagement.voucher.domain.vouchertype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTypeTest {
    @ParameterizedTest(name = "할인 금액이 {0}인 경우")
    @DisplayName("할인 금액이 0미만이거나 100000000 초과일 경우 예외 발생")
    @ValueSource(longs = {-1, 100000001})
    void discountValueUnder0AndOver100000000(long input) {
        assertThrows(IllegalArgumentException.class, () -> {
            VoucherType fixedAmountVoucherType = FixedAmountVoucherType.getInstance();
            fixedAmountVoucherType.validateDiscountValue(input);
        });
    }
}