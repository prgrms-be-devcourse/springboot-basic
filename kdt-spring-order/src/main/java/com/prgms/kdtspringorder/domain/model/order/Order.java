package com.prgms.kdtspringorder.domain.model.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgms.kdtspringorder.domain.model.voucher.Voucher;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처 없는 주문
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    // business logic
    public long calculateTotalAmount() {
        Long beforeDiscountAmount = orderItems.stream()
            .map(item -> item.getProductPrice() * item.getQuantity())
            .reduce(0L, Long::sum);

        if (voucher.isPresent()) {
            return voucher.get().discount(beforeDiscountAmount);
        } else {
            return beforeDiscountAmount;
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
