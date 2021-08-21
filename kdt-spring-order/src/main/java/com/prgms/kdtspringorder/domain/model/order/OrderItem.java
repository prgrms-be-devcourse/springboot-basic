package com.prgms.kdtspringorder.domain.model.order;

import java.util.UUID;

public class OrderItem {
    private final UUID productID;
    private final long productPrice;
    private final long quantity;

    public OrderItem(UUID productID, long productPrice, long quantity) {
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
