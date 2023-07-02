package com.wonu606.vouchermanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VoucherFactoryTest {

    VoucherFactory factory = new VoucherFactory();

    @Test
    void FIXED타입이면_FixedAmountVoucher_생성해야_한다() {
        // given
        VoucherType type = VoucherType.FIXED;
        UUID uuid = UUID.randomUUID();
        double discount = 5000;

        // when
        Voucher voucher = factory.create(type, uuid, discount);

        // then
        assertEquals(FixedAmountVoucher.class, voucher.getClass());
    }

    @Test
    void PERCENT타입이면_PercentageVoucher_생성해야_한다() {
        // given
        VoucherType type = VoucherType.PERCENT;
        UUID uuid = UUID.randomUUID();
        double discount = 50;

        // when
        Voucher voucher = factory.create(type, uuid, discount);

        // then
        assertEquals(PercentageVoucher.class, voucher.getClass());
    }
}
