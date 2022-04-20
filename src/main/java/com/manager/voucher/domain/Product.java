package com.manager.voucher.domain;

import java.util.UUID;

public class Product {
    private UUID productId;
    private String productName;
    private Long price;

    public Product(UUID productId, String productName, Long price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public void discountPriceByPercent(int percent){
        if(percent >= 100) {
            this.price = 0L;
            return;
        }
        this.price = this.price / 100 * (100 - percent);
    }

    public void discountPriceByAmount(Long amount){
        if(this.price <= amount) {
            this.price = 0L;
            return;
        }
        this.price = this.price - amount;
    }

    public String checkProductName(){
        return productName;
    }

    public Long checkPrice(){
        return price;
    }
}
