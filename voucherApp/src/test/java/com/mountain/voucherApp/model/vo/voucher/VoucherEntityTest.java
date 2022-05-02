package com.mountain.voucherApp.model.vo.voucher;

import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class VoucherEntityTest {

    @DisplayName("존재하는 할인정책은 생성 가능.")
    @Test
    void testExistPolicy() {
        for (DiscountPolicy discountPolicy : DiscountPolicy.values()) {
            Assertions.assertDoesNotThrow(
                    () -> new VoucherEntity(UUID.randomUUID(), discountPolicy,100L));
        }
    }

    @DisplayName("존재하지 않는 할인정책은 생성할 수 없다.")
    @Test
    void testNotExistPolicy() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> new VoucherEntity(UUID.randomUUID(), DiscountPolicy.valueOf("none"),100L));
    }

}