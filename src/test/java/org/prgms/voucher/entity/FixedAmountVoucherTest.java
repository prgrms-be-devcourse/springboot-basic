package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgms.voucher.exception.WrongDiscountAmountException;

class FixedAmountVoucherTest {

    @DisplayName("할인퍼센트가 1미만일때 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, -100})
    void FixedAmount_AmountLessOne_ThrowsException(long discountAmount) {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), discountAmount))
            .isInstanceOf(WrongDiscountAmountException.class)
            .hasMessage("[ERROR] 올바른 할인금액이 아닙니다.");
    }

    @DisplayName("정해진 할인금액으로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,100,900", "500,300,200", "10000,5000,5000"})
    void discount_Amount_ReturnDiscountAmount(long beforeDiscount, long discountAmount, long result) throws
        WrongDiscountAmountException {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        long discountPrice = voucher.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }
}
