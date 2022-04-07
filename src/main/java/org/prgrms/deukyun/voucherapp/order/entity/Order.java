package org.prgrms.deukyun.voucherapp.order.entity;

import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 주문
 */
public class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus;

    public Order(UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        checkArgument(customerId != null, "customerId must be provided.");
        checkArgument(orderItems != null, "orderItems must be provided.");

        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.ofNullable(voucher);
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public UUID getId() {
        return id;
    }

    /**
     * @return 주문의 전체 가격
     */
    public long totalPrice() {
        Long beforeDiscount = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
