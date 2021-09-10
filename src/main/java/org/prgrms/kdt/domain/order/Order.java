package org.prgrms.kdt.domain.order;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public class Order {
    private final Long orderId;
    private final Long customerId;
    private final List<OrderItem> orderItems;
    private final Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(Long orderId, Long customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public Order(Long orderId, Long customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Optional<Voucher> getVoucher() {
        return voucher;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
