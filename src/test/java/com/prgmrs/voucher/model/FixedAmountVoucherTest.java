package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.DiscountValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("고정 금액 바우처 테스트")
class FixedAmountVoucherTest {

    private UUID voucherId;
    private FixedAmountDiscountStrategy fixedAmountDiscountStrategy;
    private Amount amount;

    @BeforeEach
    void setUp() {
        voucherId = UUID.randomUUID();
        amount = new Amount(500);
        fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
    }

    @Test
    @DisplayName("주어진 UUID와 생성된 바우처 UUID가 같다.")
    void GetVoucherId_NoParam_SameVoucherId() {
        // Given
        Voucher voucher = new Voucher(voucherId, fixedAmountDiscountStrategy);

        // When
        UUID returnedVoucherId = voucher.voucherId();

        // Then
        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 amount와 바우처 amount가 같다.")
    void GetAmount_NoParam_SameAmountValue() {
        // Given
        Voucher voucher = new Voucher(voucherId, fixedAmountDiscountStrategy);

        // When
        Amount returnedAmount = ((FixedAmountDiscountStrategy) voucher.discountStrategy()).amount();

        // Then
        assertThat(returnedAmount.value(), is(amount.value()));
    }

    @Test
    @DisplayName("정상적으로 할인이 되었다.")
    void Discount_BeforeDiscountValue_CorrectAfterCalculation() {
        // Give
        DiscountValue beforeDiscount = new DiscountValue(900);
        Voucher voucher = new Voucher(voucherId, fixedAmountDiscountStrategy);

        // When
        DiscountValue amountAfterDiscount = voucher.discount(beforeDiscount);

        // Then
        assertThat(amountAfterDiscount.value(), is(beforeDiscount.value() - amount.value()));
    }

    @Test
    @DisplayName("할인액이 이미 현재 값보다 크다.")
    void Discount_ExceedingDiscountValue_ZeroAfterCalculation() {
        // Given
        DiscountValue discountValueBeforeDiscount = new DiscountValue(100);
        Voucher voucher = new Voucher(voucherId, fixedAmountDiscountStrategy);

        // When
        DiscountValue discountValueAfterDiscount = voucher.discount(discountValueBeforeDiscount);

        // Then
        assertThat(discountValueAfterDiscount.value(), is(0L));
    }
}