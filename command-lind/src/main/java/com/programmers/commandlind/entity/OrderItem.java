package com.programmers.commandlind.entity;

import java.util.UUID;

public class OrderItem {

    public final UUID productId;
    public final Long productPrice;
    public final int quantity;

    public OrderItem(UUID productId, Long productPrice, int quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
