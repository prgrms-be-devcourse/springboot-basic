package org.prgrms.deukyun.voucherapp.order.entity;

import java.util.UUID;

/**
 * 주문 상품
 */
public class OrderItem {

    private final UUID productId;
    private final long productPrice;
    private final long quantity;

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

    public long getQuantity() {
        return quantity;
    }
}
