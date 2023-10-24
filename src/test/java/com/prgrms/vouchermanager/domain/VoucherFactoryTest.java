package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherFactory;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherFactoryTest {

    @Test
    @DisplayName("fixed voucher 생성")
    void createVoucherFixed() {
        Voucher voucher = VoucherFactory.create(VoucherType.FIXED, 20000).get();
        assertTrue(voucher instanceof FixedAmountVoucher);
    }

    @Test
    @DisplayName("percent voucher 생성")
    void createVoucherPercent() {
        Voucher voucher = VoucherFactory.create(VoucherType.PERCENT, 20).get();
        assertTrue(voucher instanceof PercentAmountVoucher);
    }
}
