package org.prgrms.kdt.order.domain;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final long DEFAULT_TOTAL_AMOUNT = 0L;
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public long totalAmount() {
        long beforeDiscount = orderItems.stream()
                .map(v -> v.productPrice() * v.quantity)
                .reduce(DEFAULT_TOTAL_AMOUNT, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
