package com.manager.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @Test
    public void 물건이름확인(){
        product = new Product(UUID.randomUUID(),"물건1",1000L);
        Assertions.assertEquals("물건1",product.checkProductName());
    }

    @Test
    public void 물건가격확인(){
        product = new Product(UUID.randomUUID(),"물건1",20000L);
        Assertions.assertEquals(20000,product.checkPrice());
    }

    @Test
    public void 물건비율할인적용확인(){
        product = new Product(UUID.randomUUID(),"물건3",100L);
        product.discountPriceByPercent(80);
        Assertions.assertEquals(20,product.checkPrice());
    }

    @Test
    public void 백프로보다_높은_비율할인적용_확인(){
        product = new Product(UUID.randomUUID(),"물건3",100L);
        product.discountPriceByPercent(101);
        Assertions.assertEquals(0,product.checkPrice());
    }

    @Test
    public void 물건고정금액할인적용확인(){
        product = new Product(UUID.randomUUID(),"물건4",20000L);
        product.discountPriceByAmount(4000L);
        Assertions.assertEquals(16000,product.checkPrice());
    }

    @Test
    public void 물건의_가격보다_높은_고정금액할인적용_확인(){
        product = new Product(UUID.randomUUID(),"물건4",20000L);
        product.discountPriceByAmount(23000L);
        Assertions.assertEquals(0,product.checkPrice());
    }

}