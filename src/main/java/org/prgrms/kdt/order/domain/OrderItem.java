package org.prgrms.kdt.order.domain;

import java.util.UUID;

public class OrderItem {
    public final UUID productId;
    public final long productPrice;
    public final long quantity;

    public OrderItem(UUID productId, long productPrice, int quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public UUID getProductId() {
        return productId;
    }
}
