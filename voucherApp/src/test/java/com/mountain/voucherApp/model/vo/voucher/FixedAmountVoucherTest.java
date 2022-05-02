package com.mountain.voucherApp.model.vo.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new FixedAmountVoucher();
        assertEquals(900, sut.discount(1000, 100));
    }

    @Test
    @DisplayName("할인 금액이 원금보다 크다면 0원을 반환해야 한다.")
    void testMinusDiscount() {
        var sut = new FixedAmountVoucher();
        assertEquals(0, sut.discount(900, 1000));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
        // @Disabled
    void testWithMinus() {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher();
        assertThrows(IllegalArgumentException.class,
                () -> fixedAmountVoucher.validate(-100L));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
        // @Disabled
    void testVoucherCreation() {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher();

        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> fixedAmountVoucher.validate(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> fixedAmountVoucher.validate( -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> fixedAmountVoucher.validate(1000000))
        );
    }
}