package org.prgrms.kdt.domain.order;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        Long beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);

        return voucher.map(value -> value.discount(beforeDiscount))
                .orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Optional<Voucher> getVoucher() {
        return voucher;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
