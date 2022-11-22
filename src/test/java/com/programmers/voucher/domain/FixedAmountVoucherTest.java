package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

// 명명 규칙 : 메서드명_StateUnderTest_Expected behavior
// assertEquals(예상 값, 실제 값)
class FixedAmountVoucherTest {


    @Test
    @DisplayName("유효한 할인 금액으로 생성해야 한다")
    void createFixedAmountVoucher_outOfRange_throwException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 20000L))
        );
    }

    @Test
    @DisplayName("정상적인 객체 생성")
    void createFixedAmountVoucher_withinRange_instanceOfClass(){
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertThat(fixedAmountVoucher).isInstanceOf(FixedAmountVoucher.class);
    }


    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다")
    void calculateAmount_overThanBeforeAmount_becomeZero() {
        assertAll(
                () -> assertEquals(0,new FixedAmountVoucher(UUID.randomUUID(),1000L).discount(500L)),
                () -> assertEquals(0,new FixedAmountVoucher(UUID.randomUUID(),500L).discount(500L))
        );
    }

    @Test
    @DisplayName("정상적인 할인 금액")
    void calculateAmount_lessThanBeforeAmount_becomeDiscountedAmount() {
        long beforeDiscount = 30000;
        long discount = 5000;
        long expectedDiscount =  new FixedAmountVoucher(UUID.randomUUID(), discount).discount(beforeDiscount);
        assertThat(beforeDiscount - discount, equalTo(expectedDiscount));
    }

}