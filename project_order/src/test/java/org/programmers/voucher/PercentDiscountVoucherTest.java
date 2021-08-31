package org.programmers.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucherTest.class);

    @Test
    @DisplayName("주어진 확률만큼 할인이 되어야한다")
    void discount() {
        // given
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20L);

        // when
        long discountedAmount = percentDiscountVoucher.discount(100L);

        // then
        log.info("percent -> {}", percentDiscountVoucher.getValue());
        assertThat(discountedAmount, is(80L));
    }

    @Test
    @DisplayName("유효한 할인율로만 바우처를 생성할 수 있다")
    void testVoucherCreation() {
        // given

        // when

        // then
        assertAll("FixedAmountVoucher",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 150))
        );
    }

}