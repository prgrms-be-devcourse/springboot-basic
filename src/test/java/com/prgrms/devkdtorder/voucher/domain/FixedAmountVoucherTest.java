package com.prgrms.devkdtorder.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher가 올바르게 생성 되어야 한다")
    void testCreation() {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 1000L;

        //when
        FixedAmountVoucher sut = new FixedAmountVoucher(voucherId, amount);

        //then
        assertThat(sut.getVoucherId(), is(voucherId));
        assertThat(sut.getValue(), is(amount));
    }

    @Test
    @DisplayName("유효한 범위의 할인 금액으로만 생성 해야한다.")
    void testAmountValidation() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 100001L))
        );
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertThat(sut.discount(9000), is(8000L));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscount() {
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertThat(sut.discount(100), is(0L));
    }
}