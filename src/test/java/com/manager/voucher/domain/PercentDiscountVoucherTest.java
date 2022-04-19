package com.manager.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    private Voucher voucher;

    @Test
    public void 바우처비율할인적용(){
        Product product = new Product(UUID.randomUUID(),"물건1", 20000L);
        System.out.println("product.checkPrice() = " + product.checkPrice());
        voucher = new PercentDiscountVoucher(90,LocalDateTime.now().plusMonths(1));
        voucher.discountProduct(product);
        voucher.used();
        assertEquals(2000,product.checkPrice());
    }

    @Test
    public void 바우처가격보다높은비율할인적용(){
        Product product = new Product(UUID.randomUUID(),"물건1", 20000L);
        System.out.println("product.checkPrice() = " + product.checkPrice());
        voucher = new PercentDiscountVoucher(101,LocalDateTime.now().plusMonths(1));
        voucher.discountProduct(product);
        voucher.used();
        assertEquals(0,product.checkPrice());
    }

    @Test
    public void 바우처종류확인PercentDiscountVoucher(){
        voucher = new PercentDiscountVoucher(90,LocalDateTime.now().plusMonths(1));
        assertEquals("PercentDiscountVoucher",voucher.getClass().getSimpleName());
    }

}