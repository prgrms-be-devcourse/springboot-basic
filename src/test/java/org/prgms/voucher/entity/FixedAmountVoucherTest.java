package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgms.voucher.exception.ZeroDiscountAmountException;

class FixedAmountVoucherTest {

    @DisplayName("할인금액이 0인 경우 예외를 발생한다.")
    @Test
    void FixedAmount_AmountIsZero_ThrowsException() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), 0))
            .isInstanceOf(ZeroDiscountAmountException.class)
            .hasMessage("[ERROR] 할인금액이 0원입니다.");
    }

    @DisplayName("정해진 할인금액으로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,100,900", "500,300,200", "10000,5000,5000"})
    void discount_Amount_ReturnDiscountAmount(long beforeDiscount, long discountAmount, long result) throws
        ZeroDiscountAmountException {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }
}
