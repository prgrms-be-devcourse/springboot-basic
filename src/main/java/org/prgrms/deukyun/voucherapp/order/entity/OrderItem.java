package org.prgrms.deukyun.voucherapp.order.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 주문 상품
 */
public class OrderItem {

    private final UUID productId;
    private final long productPrice;
    private final long quantity;

    public OrderItem(UUID productId, long productPrice, int quantity) {
        checkArgument(productId != null, "productId must be provided.");
        checkArgument(
                productPrice > 0 && productPrice< 10_000_000,
                "productPrice must be between 0 and 10_000_000 exclusive."
        );
        checkArgument(
                quantity > 0 && quantity < 1000,
                "quantity must be between 0 and 1000 exclusive."
        );

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

    public long getTotalPrice() {
        return productPrice * quantity;
    }
}
