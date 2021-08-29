package org.prgrms.orderapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 100);

        assertThat(sut.discount(1000), is(900L));
    }

    @Test
    @DisplayName("디스카운트 된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountAmount() {
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        assertThat(sut.discount(900), is(0L));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        Throwable thrown = catchThrowable(() -> new FixedAmountVoucher(UUID.randomUUID(), -100));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("amount should be positive");
    }

    @Test
    @DisplayName("유효한 금액으로만 설정할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), 1000000))
        );
    }

    @Test
    @DisplayName("바우처 아이디를 가져올 수 있다.")
    void testGetVoucherId() {
        UUID id = UUID.randomUUID();
        FixedAmountVoucher sut = new FixedAmountVoucher(id, 1000);
        assertThat(sut.getVoucherId(), is(id));
    }
}