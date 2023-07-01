package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {
    @Test
    @DisplayName("find 고정된 금액의 정책")
    void findByPolicyFixedVoucherPolicy() {
        String policy = "1";
        Optional<VoucherType> voucherPolicy = VoucherType.findByPolicy(policy);

        assertEquals(VoucherType.FIXED_AMOUNT_VOUCHER, voucherPolicy.get());
    }

    @Test
    @DisplayName("find 할인율 정책")
    void findByPolicyPercentVoucherPolicy() {
        String policy = "2";
        Optional<VoucherType> voucherPolicy = VoucherType.findByPolicy(policy);

        assertEquals(VoucherType.PERCENT_DISCOUNT_VOUCHER, voucherPolicy.get());
    }

    @Test
    @DisplayName("없는 할인 정책에 대한 예외 테스트")
    void findByPolicyNotExistVoucherPolicy() {
        String policy = "3";
        Optional<VoucherType> voucherPolicy = VoucherType.findByPolicy(policy);

        assertEquals(Optional.empty(), voucherPolicy);
    }

}