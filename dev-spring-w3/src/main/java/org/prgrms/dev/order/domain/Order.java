package org.prgrms.dev.order.domain;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.ArrayList;
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
    private final OrderStatus orderStatus = OrderStatus.ACCECPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        // 주무한 목록을 순회하면서 아이템의 가격과 개수를 곱한 총합
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);

        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

}

