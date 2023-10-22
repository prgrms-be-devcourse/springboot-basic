package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.io.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherFactoryTest {

    @Test
    @DisplayName("fixed voucher 생성")
    void createVoucherFixed() {
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, 20000).get();
        assertTrue(voucher instanceof FixedAmountVoucher);
    }

    @Test
    @DisplayName("percent voucher 생성")
    void createVoucherPercent() {
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.PERCENT, 20).get();
        assertTrue(voucher instanceof PercentAmountVoucher);
    }
}
