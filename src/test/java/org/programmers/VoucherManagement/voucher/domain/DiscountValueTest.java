package org.programmers.VoucherManagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DiscountValueTest {

    @Test
    @DisplayName("DiscountValue를 생성할 수 있다. - 성공")
    void DiscountValue_intValue_createDiscountValue() {
        //given
        int value = 50;

        //when
        DiscountValue discountValueExpect = new DiscountValue(value);

        //then
        assertThat(discountValueExpect.getValue()).isEqualTo(50);
    }
}
