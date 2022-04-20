package org.prgrms.deukyun.voucherapp.domain.order.entity;

import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 주문
 */
public class Order {

    private UUID id;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private final Optional<Voucher> voucher;
    private final OrderStatus orderStatus;

    private Order(UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        checkArgument(customerId != null, "customerId must be provided.");
        checkArgument(orderItems != null, "orderItems must be provided.");
        checkArgument(!orderItems.isEmpty(), "orderItems must contains at least one OrderItem.");

        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.ofNullable(voucher);
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public static Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        return new Order(customerId, orderItems, null);
    }

    public static Order createOrderWithVoucher(UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        checkArgument(voucher != null, "voucher must be provided in this method.");

        return new Order(customerId, orderItems, voucher);
    }

    public UUID getId() {
        return id;
    }

    /**
     * @return 주문의 전체 가격
     */
    public long totalPrice() {
        Long beforeDiscountPrice = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscountPrice)).orElse(beforeDiscountPrice);
    }

    public UUID getCustomerId() {
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
