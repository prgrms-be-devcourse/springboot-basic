package org.prgrms.orderapp.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucherTest.class);

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        PercentDiscountVoucher sut = new PercentDiscountVoucher(UUID.randomUUID(), 40);

        assertThat(sut.discount(1000), is(600L));
    }

    @Test
    @DisplayName("할인률은 마이너스가 될 수 없다.")
    void testWithMinus() {
        Throwable thrown = catchThrowable(() -> new PercentDiscountVoucher(UUID.randomUUID(), -1));
        Assertions.assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("percent should be positive");
    }

    @Test
    @DisplayName("유효한 금액으로만 설정할 수 있다.")
    void testVoucherCreation() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, ()-> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, ()-> new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, ()-> new PercentDiscountVoucher(UUID.randomUUID(), 101))
        );
    }

    @Test
    @DisplayName("바우처 아이디를 가져올 수 있다.")
    void testGetVoucherId() {
        UUID id = UUID.randomUUID();
        PercentDiscountVoucher sut = new PercentDiscountVoucher(id, 20);
        assertThat(sut.getVoucherId(), is(id));
    }

}