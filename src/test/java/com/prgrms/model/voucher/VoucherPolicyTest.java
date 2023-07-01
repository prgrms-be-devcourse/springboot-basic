package com.prgrms.model.voucher;

import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherPolicyTest {
    @Test
    @DisplayName("find 고정된 금액의 정책")
    void findByPolicyFixedVoucherPolicy() {
        String policy = "1";
        Optional<VoucherPolicy> voucherPolicy = VoucherPolicy.findByPolicy(policy);

        assertEquals(VoucherPolicy.FixedAmountVoucher, voucherPolicy.get());
    }

    @Test
    @DisplayName("find 할인율 정책")
    void findByPolicyPercentVoucherPolicy() {
        String policy = "2";
        Optional<VoucherPolicy> voucherPolicy = VoucherPolicy.findByPolicy(policy);

        assertEquals(VoucherPolicy.PercentDiscountVoucher, voucherPolicy.get());
    }

    @Test
    @DisplayName("없는 할인 정책에 대한 예외 테스트")
    void findByPolicyNotExistVoucherPolicy() {
        String policy = "3";
        Optional<VoucherPolicy> voucherPolicy = VoucherPolicy.findByPolicy(policy);

        assertEquals(Optional.empty(), voucherPolicy);
    }

}