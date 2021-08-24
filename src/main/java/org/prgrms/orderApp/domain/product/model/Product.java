package org.prgrms.orderApp.domain.product.model;

import java.util.UUID;

// Entity
public class Product implements ProductModel{
    private UUID productId;
    private String productName;
    private Long productPrice;

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }
}
