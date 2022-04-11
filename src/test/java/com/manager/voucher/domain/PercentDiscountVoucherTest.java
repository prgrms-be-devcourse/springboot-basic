package com.manager.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private Voucher voucher;

    @Test
    public void 확인FixedDiscountVoucher(){
        assertEquals(voucher.getClass().getSimpleName(),"FixedDiscountVoucher");
    }

    @Test
    public void toString함수확인(){
        assertEquals(voucher.toString(), "percent discount: 50");
    }

    @Test
    public void 바우처만료일지남(){
        assertEquals(true,voucher.isExpired());
    }
}