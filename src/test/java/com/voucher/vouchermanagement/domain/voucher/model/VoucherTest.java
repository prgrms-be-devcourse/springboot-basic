package com.voucher.vouchermanagement.domain.voucher.model;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.voucher.vouchermanagement.exception.NotValidValueException;

class VoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher는 최대 0원까지 할인이 된다.")
    void fixedAmountVoucherFailingDiscount() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());

        //when
        long finalPrice = voucher.discount(99);

        //then
        assertThat(finalPrice, is(0L));
    }

    @Test
    @DisplayName("FixedAmountVoucher는 100원 할인 테스트")
    void fixedAmountVoucherSuccessDiscountTest() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());

        //when
        long finalPrice = voucher.discount(150);

        //then
        assertThat(finalPrice, is(50L));
    }

    @Test
    @DisplayName("FixedAmountVoucher는 바우처 할인액이 1원 미만일 수 없다.")
    public void fixedAmountCannotDiscountUnderMinTest() {
        assertThrows(NotValidValueException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), 0L, LocalDateTime.now()));
    }

    @Test
    @DisplayName("percentDiscountVoucher 50% 할인 테스트")
    void percentDiscountVoucher50PercentDiscountTest() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50L, LocalDateTime.now());

        //when
        long finalPrice = voucher.discount(100L);

        //then
        assertThat(finalPrice, is(50L));
    }

    @Test
    @DisplayName("percentDiscountVoucher 100% 할인 테스트")
    void percentDiscountVoucher100PercentDiscountTest() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());

        //when
        long finalPrice = voucher.discount(100L);

        //then
        assertThat(finalPrice, is(0L));
    }

    @Test
    @DisplayName("percentDiscountVoucher는 0% 할인은 안된다.")
    void percentDiscountVoucherCannotDiscountUnderMinTest() {
        assertThrows(NotValidValueException.class,
                () -> new PercentDiscountVoucher(UUID.randomUUID(), 0L, LocalDateTime.now()));
    }

    @Test
    @DisplayName("percentDiscountVoucher는 100% 초과 할인은 안된다.")
    void percentDiscountVoucherCannotDiscountOverMaxTest() {
        assertThrows(NotValidValueException.class,
                () -> new PercentDiscountVoucher(UUID.randomUUID(), 101L, LocalDateTime.now()));
    }
}