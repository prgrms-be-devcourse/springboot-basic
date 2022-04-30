package org.prgrms.spring_week1.Voucher.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("할인율은 음수가 될 수 없다.")
    void minusPercentTest(){
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10, UUID.randomUUID()));
    }

    @Test
    @DisplayName("할인율은 100을 넘을 수 없다.")
    void bigPercentTest(){
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 101, UUID.randomUUID()));
    }


}