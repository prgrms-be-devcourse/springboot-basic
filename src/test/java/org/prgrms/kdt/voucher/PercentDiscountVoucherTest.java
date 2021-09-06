package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 할인율만큼 할인을 해야 한다.")
    void testDiscountRate() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 50);
        assertThat(sut.discount(1000), is(500L));
    }

    @Test
    @DisplayName("할인율은 마이너스가 될 수 없다.")
    void testMinusDiscountRate() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10));
    }

    @Test
    @DisplayName("유효한 할인율로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 100))
        );
    }

}