package org.prgrms.orderApp.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.orderApp.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
/**
 * Created by yunyun on 2021/10/06.
 */
class FixedAmountVoucherTest {


    @Test
    @DisplayName("할인된 가격을 도출할 수 있다.")
    void discountTest() {
        // Given
        long beforeDiscount = 5000L;
        long discount = 2000L;

        // When
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
        var afterDiscount = fixedAmountVoucher.discount(beforeDiscount);

        // Then
        assertThat(fixedAmountVoucher.getVoucherAmount(), is(discount));
        assertThat(afterDiscount, is(3000L));
    }

    @Test
    @DisplayName("할인 가격은 상품 가격보다 작거나 같아야 합니다.")
    void validatingAmountTest1(){
        // Given
        long beforeDiscount = 2000L;
        long discount = 5000L;
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);


        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            fixedAmountVoucher.discount(beforeDiscount);
        });

    }

    @Test
    @DisplayName("할인가는 0이상이어야 한다.")
    void validatingAmountTest2(){
        // Given
        long invalidAmount1 = 0L;
        long invalidAmount2 = -1L;
        long validAmount = 100L;


        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            new FixedAmountVoucher(UUID.randomUUID(), invalidAmount1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new FixedAmountVoucher(UUID.randomUUID(), invalidAmount2);
        });
        assertThat(new FixedAmountVoucher(UUID.randomUUID(), validAmount).getVoucherAmount(), is(100L));

    }
}