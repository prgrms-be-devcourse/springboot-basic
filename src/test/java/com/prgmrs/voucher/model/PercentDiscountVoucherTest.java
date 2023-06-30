package com.prgmrs.voucher.model;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("주어진 UUID와 바우처 UUID가 같은 경우")
    void getVoucherIdTest() {
        UUID voucherId = UUID.randomUUID();
        long percent = 50;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

        UUID returnedVoucherId = voucher.getVoucherId();

        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 percent 바우처 percent 같은 경우")
    void getAmountTest() {
        UUID voucherId = UUID.randomUUID();
        long percent = 500;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

        long returnedPercent = voucher.getPercent();

        assertThat(returnedPercent, is(percent));
    }

    @Test
    @DisplayName("할인률에따른 할인액")
    void discountTest() {
        UUID voucherId = UUID.randomUUID();
        long percent = 34;
        long beforeDiscount = 1000;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);
        System.out.println(voucher.getPercent());
        long discountValue = voucher.discount(beforeDiscount);

        assertThat(discountValue, is( (beforeDiscount/100 * percent) ));
    }

    @Test
    @DisplayName("할인률이 100%가 넘어가는 경우")
    void discountAmountIsSmallerThanValueBeforeDiscount() {
        UUID voucherId = UUID.randomUUID();
        long percent = 1000;
        long valueBeforeDiscount = 500;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

        Throwable exception = assertThrows(WrongRangeFormatException.class, () -> {
            voucher.discount(valueBeforeDiscount);
        });

        assertThat(exception.getMessage(), is("percent not between 1-100"));

    }
}