package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class PercentAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(PercentAmountVoucherTest.class);


    @Test
    @DisplayName("주어진 비율만큼 할인을 해야한다.")
    void discount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 15L);
        assertEquals(850L, sut.discount(1000L));
        assertThat(sut.discount(1000), equalTo(850L));
    }

    @Test
    @DisplayName("유효한 할인 비율로만 생성 할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(),  -10)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(),  200))
        );
    }

    @Test
    @DisplayName("할인 비율은 마이너스가 될 수 없다.")
    @Disabled
    void testWithMinus(){
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100));
    }

}