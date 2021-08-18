package org.prgrms.kdt.domain;

import org.prgrms.kdt.strategy.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private final Optional<Voucher> voucher;
    private OrderStatus orderStatus;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Optional<Voucher> voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = voucher;
    }
}
