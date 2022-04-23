package org.prgrms.kdt.shop.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("FixedAmountVoucher 할인 테스트")
    void discount( ) {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        long money = 10000;
        //when
        long afterMoney = fixedAmountVoucher.discount(money);
        //then
        assertThat(afterMoney, is(9990L));
    }
}