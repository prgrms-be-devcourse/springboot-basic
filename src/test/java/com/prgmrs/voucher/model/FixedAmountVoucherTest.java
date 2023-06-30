package com.prgmrs.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("고정 금액 바우처 테스트")
class FixedAmountVoucherTest {
    @Test
    @DisplayName("주어진 UUID와 바우처 UUID가 같은 경우")
    void getVoucherIdTest() {
        UUID voucherId = UUID.randomUUID();
        long amount = 10000;
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        UUID returnedVoucherId = voucher.getVoucherId();

        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 amount와 바우처 amount 같은 경우")
    void getAmountTest() {
        UUID voucherId = UUID.randomUUID();
        long amount = 500;
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        long returnedAmount = voucher.getAmount();

        assertThat(returnedAmount, is(amount));
    }

    @Test
    @DisplayName("할인된 값이 올바른 경우")
    void discountTest() {
        UUID voucherId = UUID.randomUUID();
        long amount = 1000;
        long beforeDiscount = 5000;
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        long amountAfterDiscount = voucher.discount(beforeDiscount
        );

        assertThat(amountAfterDiscount, is(beforeDiscount - amount));
    }

    @Test
    @DisplayName("할인액이 할인전 값보다 작은 경우")
    void discountAmountIsSmallerThanValueBeforeDiscount() {
        UUID voucherId = UUID.randomUUID();
        long amount = 1000;
        long valueBeforeDiscount = 500;
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        long valueAfterDiscount = voucher.discount(valueBeforeDiscount);

        assertThat(valueAfterDiscount, is(0L));
    }
}