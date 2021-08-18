package org.prgrms.kdt.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
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
