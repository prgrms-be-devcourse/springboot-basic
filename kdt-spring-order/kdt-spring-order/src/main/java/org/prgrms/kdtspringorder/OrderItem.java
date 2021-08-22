package org.prgrms.kdtspringorder;

import java.util.UUID;

public class OrderItem {
    public final UUID productId;
    public final long productPrice;
    public  final long quantity;

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

