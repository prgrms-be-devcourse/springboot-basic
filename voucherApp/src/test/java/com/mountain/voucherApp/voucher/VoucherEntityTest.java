package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import com.mountain.voucherApp.shared.utils.DiscountPolicyUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.UUID;

class VoucherEntityTest {

    @DisplayName("존재하는 할인정책은 생성 가능.")
    @Test
    void testExistPolicy() {
        Map<Integer, DiscountPolicy> discountPolicyMap = DiscountPolicyUtil.getDiscountPolicyMap();
        for (Integer existPolicyId : discountPolicyMap.keySet()) {
            Assertions.assertDoesNotThrow(
                    () -> new VoucherEntity(UUID.randomUUID(), existPolicyId,100L));
        }
    }

    @DisplayName("존재하지 않는 할인정책은 생성할 수 없다.")
    @ParameterizedTest
    @CsvSource({ // given
            "0",
            "-1",
            "3"
    })
    void testNotExistPolicy(int discountPolicyId) {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> new VoucherEntity(UUID.randomUUID(), discountPolicyId,100L));
    }

}