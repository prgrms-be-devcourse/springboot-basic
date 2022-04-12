package com.manager.voucher.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private Voucher voucher;

    @Test
    public void 확인FixedDiscountVoucher(){
        voucher = new PercentDiscountVoucher(20, LocalDateTime.now().plusMonths(1));
        assertEquals(voucher.getClass().getSimpleName(),"PercentDiscountVoucher");
    }

    @Test
    public void toString함수확인(){
        voucher = new PercentDiscountVoucher(50,LocalDateTime.now().plusMonths(1));
        assertEquals(voucher.toString(), "percent discount: 50");
    }

    @Test
    public void 바우처만료일지남(){
        voucher = new PercentDiscountVoucher(40,LocalDateTime.now().minusMonths(1));
        assertEquals(true,voucher.isExpired());
    }

    @Test
    public void 바우처만료일안지남(){
        voucher = new PercentDiscountVoucher(40,LocalDateTime.now().plusMonths(1));
        assertEquals(false,voucher.isExpired());
    }
    //바우처 사용에대한 내용

    @Test
    public void 할인적용Percent(){
        Item item = new Item(100000);
        voucher = new PercentDiscountVoucher(40,LocalDateTime.now().plusMonths(1));
        voucher.applyDiscountTo(item);
        assertEquals(60000,item.getPrice());
    }
}