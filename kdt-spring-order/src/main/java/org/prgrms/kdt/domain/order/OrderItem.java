package org.prgrms.kdt.domain.order;

import java.util.UUID;

public class OrderItem {
    public final UUID product_id;
    public final long productPrice;
    public final long quantity;

    public OrderItem(UUID product_id, long productPrice, int quantity) {
        this.product_id = product_id;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
