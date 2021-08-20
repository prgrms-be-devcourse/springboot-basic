package org.prgrms.kdt.domain.order.domain;

import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID order_id;
    private final UUID customer_id;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID order_id, UUID customer_id, List<OrderItem> orderItems) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID order_id, UUID customer_id, List<OrderItem> orderItems, Voucher voucher) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
