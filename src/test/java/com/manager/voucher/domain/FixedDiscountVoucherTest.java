package com.manager.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountVoucherTest {

    Voucher voucher;

    @Test
    public void 확인FixedDiscountVoucher(){
        voucher = new FixedDiscountVoucher(52000, LocalDateTime.now().plusMonths(1));
        assertEquals(voucher.getClass().getSimpleName(),"FixedDiscountVoucher");
    }

    @Test
    public void toString함수확인(){
        voucher = new FixedDiscountVoucher(5000, LocalDateTime.now().plusMonths(1));
        assertEquals(voucher.toString(), "fixed Amount: 5000");
    }

    @Test
    public void 바우처만료일안지남(){
        voucher = new FixedDiscountVoucher(15000, LocalDateTime.now().plusMonths(1));
        assertEquals(false,voucher.isExpired());
    }

    @Test
    public void 바우처만료일지남(){
        voucher = new FixedDiscountVoucher(12000,LocalDateTime.now().minusMonths(1));
        assertEquals(true,voucher.isExpired());
    }

    @Test
    public void 할인적용Amount(){
        Item item = new Item(10000);
        voucher = new FixedDiscountVoucher(400,LocalDateTime.now().plusMonths(1));
        voucher.applyDiscountTo(item);
        assertEquals(9600,item.getPrice());
    }
}