package org.prgrms.deukyun.voucherapp.order;

import org.prgrms.deukyun.voucherapp.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus;

    public Order(UUID id, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public Order(UUID id, UUID customerId, List<OrderItem> orderItems) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public UUID getId() {
        return id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long totalAmount() {
        Long beforeDiscount = orderItems.stream()
                .map(oi -> oi.getProductPrice() * oi.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }
}
