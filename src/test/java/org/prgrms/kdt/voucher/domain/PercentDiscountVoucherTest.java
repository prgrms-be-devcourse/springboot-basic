package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("[실패] 사용자 입력값에 따른 유효성 검사")
    void validate_FAIL() {
        assertAll("PercentAmount Voucher create",
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 100000))
        );
    }

    @Test
    @DisplayName("[성공] 할인 금액에 따른 유효성 검사")
    void discount_SUCCESS() {
        // given
        long beforeDiscount = 100L;
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        // then
        long actual = voucher.discount(beforeDiscount);

        // when
        assertEquals(8100L, actual);
    }

    @Test
    @DisplayName("[실패] 할인 금액에 따른 유효성 검사")
    void discount_FAIL() {
        long beforeDiscount = 100;
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), -10);
        assertThrows(InvalidArgumentException.class, () -> voucher.discount(beforeDiscount));
    }

}