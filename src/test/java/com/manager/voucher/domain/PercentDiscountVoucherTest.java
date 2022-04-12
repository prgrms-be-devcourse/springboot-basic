package com.manager.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private Voucher voucher;

    @Test
    public void 확인FixedDiscountVoucher(){
        voucher = new PercentDiscountVoucher(20);
        assertEquals(voucher.getClass().getSimpleName(),"PercentDiscountVoucher");
    }

    @Test
    public void toString함수확인(){
        voucher = new PercentDiscountVoucher(50);
        assertEquals(voucher.toString(), "percent discount: 50");
    }

    @Test
    public void 바우처만료일지남(){
        voucher = new PercentDiscountVoucher(40);
        assertEquals(false,voucher.isExpired());
    }
}