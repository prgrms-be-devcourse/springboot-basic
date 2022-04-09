package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private UUID orderId;
    private UUID customerId;
    private List<OrderItem> orderItemList;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED; // 주문 상태

    public static class Builder {
        private UUID orderId;
        private UUID customerId;
        private List<OrderItem> orderItemList;
        private Optional<Voucher> voucher = Optional.empty();

        public Builder(UUID orderId, UUID customerId, List<OrderItem> orderItemList) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.orderItemList = orderItemList;
        }

        public Builder setVoucher(Voucher voucher) {
            this.voucher = Optional.ofNullable(voucher);
            return this;
        }

        public Order build( ) {
            return new Order(this);
        }
    }

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.orderItemList = builder.orderItemList;
        this.voucher = builder.voucher;
    }

    public long totalAmount( ) {
        var beforeDiscount = orderItemList.stream().map(v -> v.getProductPrice() * v.getQuantity()).reduce(0L, Long::sum);

        if (voucher.isPresent()) return voucher.get().discount(beforeDiscount);
        else {
            return beforeDiscount;
        }
    }
}
