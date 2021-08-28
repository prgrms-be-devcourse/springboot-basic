package org.prgrms.dev.order.domain;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Entity Class
public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    // 느슨한 결합도로 바꾸고 싶다? → 런타임 의존성을 갖도록 → interface !!!
    // private FixedAmountVoucher fixedAmountVoucher;
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
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Optional<Voucher> getVoucher() {
        return voucher;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public UUID getCustomerId() {
        return customerId;
    }

}

