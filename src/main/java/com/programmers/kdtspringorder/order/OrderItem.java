package com.programmers.kdtspringorder.order;

import java.util.UUID;

public class OrderItem {
    private final UUID productID;
    public final long productPrice;
    public final long quantity;

    public OrderItem(UUID productID, long productPrice, int quantity) {
        this.productID = productID;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductID() {
        return productID;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
