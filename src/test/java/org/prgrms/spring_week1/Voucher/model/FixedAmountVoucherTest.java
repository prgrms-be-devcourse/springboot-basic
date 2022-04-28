package org.prgrms.spring_week1.Voucher.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("할인가격은 음수가 될 수 없다.")
    void minusAmountTest(){
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -10, UUID.randomUUID()));
    }

    @Test
    @DisplayName("할인가격은 천만원을 넘을 수 없다.")
    void bigAmountTest(){
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10000001, UUID.randomUUID()));
    }

    @Test
    @DisplayName("할인 후 금액은 음수가 될 수 없다.")
    void discountTest() {
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 100, UUID.randomUUID());
        assertEquals(0, sut.discount(50));
    }

}