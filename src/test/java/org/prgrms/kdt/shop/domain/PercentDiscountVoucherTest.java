package org.prgrms.kdt.shop.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("PercentDiscountVoucher 할인 테스트")
    void discount( ) {
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());
        long money = 10000;
        //when
        long afterMoney = percentDiscountVoucher.discount(money);
        //then
        assertThat(afterMoney, is(9000L));
    }

}