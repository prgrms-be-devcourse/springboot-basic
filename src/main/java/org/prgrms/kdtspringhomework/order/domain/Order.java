package org.prgrms.kdtspringhomework.order.domain;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private static final long LONG_IDENTITY = 0L;

    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private final Optional<Voucher> voucher;
    private final OrderStatus orderStatus;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
        orderStatus = OrderStatus.ACCEPTED;
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
        orderStatus = OrderStatus.ACCEPTED;
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(orderItem -> orderItem.getProductPrice() * orderItem.getQuantity())
                .reduce(LONG_IDENTITY, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }
}
