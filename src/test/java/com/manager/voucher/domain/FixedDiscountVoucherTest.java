package com.manager.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountVoucherTest {

    Voucher voucher;

    @Test
    public void 바우처고정할인적용(){
        Product product = new Product(UUID.randomUUID(),"물건2", 10000L);
        voucher = new FixedDiscountVoucher(9000L,LocalDateTime.now().plusMonths(1));

        voucher.discountProduct(product);
        voucher.used();

        assertEquals(1000,product.checkPrice());
    }

    @Test
    public void 바우처물건보다높은고정할인적용(){
        Product product = new Product(UUID.randomUUID(),"물건2", 10000L);
        voucher = new FixedDiscountVoucher(10100L,LocalDateTime.now().plusMonths(1));

        voucher.discountProduct(product);
        voucher.used();

        assertEquals(0,product.checkPrice());
    }

    @Test
    public void 바우처종류확인FixedDiscountVoucher(){
        voucher = new FixedDiscountVoucher(111111L,LocalDateTime.now().plusMonths(1));

        assertEquals("FixedDiscountVoucher",voucher.getClass().getSimpleName());
    }

}