package org.devcourse.voucher.voucher.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PercentDiscountVoucherTest {

    private final PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

    @Test
    @DisplayName("실제 할인률 만큼 금액이 차감되는지 테스트")
    void discountTest() {
        assertThat(voucher.discount(1000)).isEqualTo(900);
    }

    @Test
    @DisplayName("100이 넘는 할인률이 값으로 들어오는 경우 일괄적으로 100으로 변경하는지 테스트")
    void discountOneHundredOverValueTest() {
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 200);
        assertThat(voucher.getDiscount()).isEqualTo(100);
    }

    @Test
    @DisplayName("상품의 가격이 음수로 들어올 경우 예외 발생하는지 테스트")
    void priceNegativeValueTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> voucher.discount(-11));
    }
}