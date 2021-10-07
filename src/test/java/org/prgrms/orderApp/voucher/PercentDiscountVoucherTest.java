package org.prgrms.orderApp.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/06.
 */
class PercentDiscountVoucherTest {

    @Test
    @DisplayName("할인된 가격을 도출할 수 있다.")
    void discountTest() {
        // Given
        long beforeDiscount = 5000L;
        long discount = 30L;

        // When
        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
        var afterDiscount = percentDiscountVoucher.discount(beforeDiscount);

        // Then
        assertThat(percentDiscountVoucher.getVoucherAmount(), is(discount));
        assertThat(afterDiscount, is(3500L));
    }


    @Test
    @DisplayName("할인 적용 퍼센트는 0초과 100이하이어야 한다.")
    void validatingAmountTest2(){
        // Given
        long invalidAmount1 = 200L;
        long invalidAmount2 = -1L;
        long validAmount1 = 100L;
        long validAmount2 = 10L;


        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(UUID.randomUUID(), invalidAmount1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PercentDiscountVoucher(UUID.randomUUID(), invalidAmount2);
        });
        assertThat(new PercentDiscountVoucher(UUID.randomUUID(), validAmount1).getVoucherAmount(), is(100L));
        assertThat(new PercentDiscountVoucher(UUID.randomUUID(), validAmount2).getVoucherAmount(), is(10L));

    }
}