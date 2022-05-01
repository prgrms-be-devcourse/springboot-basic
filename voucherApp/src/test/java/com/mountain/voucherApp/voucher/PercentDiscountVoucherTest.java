package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.domain.PercentDiscountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher();
        assertEquals(900, sut.discount(1000, 10));
    }

    @Test
    @DisplayName("100퍼 센트 할인은 공짜(0원) 이다.")
    void testMinusDiscount() {
        var sut = new PercentDiscountVoucher();
        assertEquals(0, sut.discount(900, 100));
    }

    @Test
    @DisplayName("할인율은 마이너스가 될 수 없다.")
        // @Disabled
    void testWithMinus() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher();
        assertThrows(IllegalArgumentException.class,
                () -> percentDiscountVoucher.validate(-100L));
    }

    @Test
    @DisplayName("유효한 할인율로만 생성할 수 있다.")
        // @Disabled
    void testVoucherCreation() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher();
        assertAll("PercentAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> percentDiscountVoucher.validate(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> percentDiscountVoucher.validate(-100)),
                () -> assertThrows(IllegalArgumentException.class, () -> percentDiscountVoucher.validate( 101))
        );
    }
}