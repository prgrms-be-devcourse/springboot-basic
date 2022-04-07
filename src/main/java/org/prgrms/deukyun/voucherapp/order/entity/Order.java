package org.prgrms.deukyun.voucherapp.order.entity;

import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 주문
 */
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

    public UUID getId() {
        return id;
    }

    /**
     * @return 주문의 전체 가격
     */
    public long totalPrice() {
        Long beforeDiscount = orderItems.stream()
                .map(oi -> oi.getProductPrice() * oi.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }
}
