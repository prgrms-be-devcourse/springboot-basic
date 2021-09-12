package org.prgrms.kdt.domain.order;

public class OrderItem {
    private final Long productId;
    private final long productPrice;
    private final long quantity;

    public OrderItem(Long productId, long productPrice, long quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
