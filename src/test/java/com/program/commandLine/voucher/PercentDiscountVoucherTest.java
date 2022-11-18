package com.program.commandLine.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PercentDiscountVoucherTest {


    // Given
    UUID voucherId = UUID.randomUUID();
    Voucher percentDiscountVoucherSut = new PercentDiscountVoucher(voucherId, 30);

    @Test
    @DisplayName("Voucher의 할인금액을 얻을 수 있다.")
    void getVoucherDiscount() {
        // When
        var getType = percentDiscountVoucherSut.getVoucherDiscount();
        // Then
        assertThat(getType, is(30));
    }

    @Test
    @DisplayName("Voucher의 주어진 퍼센트만큼 할인이 된다.")
    void discountPrice() {
        var discountPrice = percentDiscountVoucherSut.discountPrice(1000);
        // Then
        assertThat(discountPrice, is(700));
    }

    @Test
    @DisplayName("유효한 퍼센트 할인만 생성된다.")
    void voucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 100)));
    }
}