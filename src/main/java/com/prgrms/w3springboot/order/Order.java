package com.prgrms.w3springboot.order;

import com.prgrms.w3springboot.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private static final long INITIAL_VALUE = 0L;

    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private final Optional<Voucher> voucher;
    private final OrderStatus orderStatus;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(INITIAL_VALUE, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public UUID getOrderId() {
        return orderId;
    }

}
