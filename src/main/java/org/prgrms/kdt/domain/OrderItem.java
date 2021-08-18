package org.prgrms.kdt.domain;

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
}
