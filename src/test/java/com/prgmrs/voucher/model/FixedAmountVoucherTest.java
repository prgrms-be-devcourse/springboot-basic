package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("고정 금액 바우처 테스트")
class FixedAmountVoucherTest {
    @Test
    @DisplayName("주어진 UUID와 생성된 바우처 UUID가 같다.")
    void getVoucherIdTest() {
        UUID voucherId = UUID.randomUUID();
        Amount amount = new Amount(10000);
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        UUID returnedVoucherId = voucher.getVoucherId();

        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 amount와 바우처 amount가 같다.")
    void getAmountTest() {
        UUID voucherId = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        long returnedAmount = voucher.getAmount().getValue();

        assertThat(returnedAmount, is(amount.getValue()));
    }

    @Test
    @DisplayName("정상적으로 할인이 되었다.")
    void discountTest() {
        UUID voucherId = UUID.randomUUID();
        Amount amount = new Amount(1000);
        DiscountValue beforeDiscount = new DiscountValue(5000);
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        DiscountValue amountAfterDiscount = voucher.discount(beforeDiscount);

        assertThat(amountAfterDiscount.getValue(), is(beforeDiscount.getValue() - amount.getValue()));
    }

    @Test
    @DisplayName("할인액이 이미 현재 값보다 크다.")
    void discountAmountIsSmallerThanValueBeforeDiscount() {
        UUID voucherId = UUID.randomUUID();
        Amount amount = new Amount(1000);
        DiscountValue discountValueBeforeDiscount = new DiscountValue(500);
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, amount);

        DiscountValue discountValueAfterDiscount = voucher.discount(discountValueBeforeDiscount);

        assertThat(discountValueAfterDiscount.getValue(), is(0L));
    }
}