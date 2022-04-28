package org.prgrms.spring_week1.order.model;

import java.util.UUID;

public class OrderItem {

    private final UUID productId;
    private final long productPrice;
    private final long quantity;

    public OrderItem(UUID productId, long productPrice, long quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
