package org.prgrms.kdt.kdtspringorder;

import java.util.UUID;

public class OrderItem {

    private final UUID productId;
    private final long productPrice;
    private final int quantity;

    public OrderItem(UUID productId, long productPrice, int quantity) {
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

    public int getQuantity() {
        return quantity;
    }
}
