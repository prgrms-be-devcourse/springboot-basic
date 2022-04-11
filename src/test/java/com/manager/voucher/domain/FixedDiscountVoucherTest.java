package com.manager.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountVoucherTest {

    Voucher voucher;

    @Test
    public void 확인FixedDiscountVoucher(){
        assertEquals(voucher.getClass().getSimpleName(),"FixedDiscountVoucher");
    }

    @Test
    public void toString함수확인(){
        assertEquals(voucher.toString(), "fixed Amount: 5000");
    }

    @Test
    public void 바우처만료일지남(){
        assertEquals(true,voucher.isExpired());
    }
}