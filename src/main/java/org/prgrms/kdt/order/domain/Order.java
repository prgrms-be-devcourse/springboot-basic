package org.prgrms.kdt.order.domain;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private static final Long INIT_BEFORE_DISCOUNT = 0L;

    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) { // 다음 강의에서 사
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    // voucher가 없을 때
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(voucher -> voucher.getProductPrice() * voucher.getQuantity())
                .reduce(INIT_BEFORE_DISCOUNT, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public UUID getOrderId() {
        return orderId;
    }
}