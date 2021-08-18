package org.prgrms.kdt;

import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID order_id;
    private final UUID customer_id;
    private final List<OrderItem> orderItems;
    private Voucher voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID order_id, UUID customer_id, List<OrderItem> orderItems, Voucher voucher) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.orderItems = orderItems;
        this.voucher = voucher;
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.discount(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
