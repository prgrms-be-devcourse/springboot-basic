package org.prgrms.spring_week1.Order.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.Voucher;

public class Order {

    UUID orderId;
    UUID customerId;
    private List<OrderItem> orderItemList;
    private Optional<Voucher> voucher; // 없을 수도 있음
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처 없는 경우
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.voucher = Optional.empty();
    }

    // 바우처 있는경우
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.voucher =  Optional.of(voucher);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long totalPrice() {
        Long totalPrice = orderItemList
            .stream()
            .map(i -> i.getProductPrice() * i.getQuantity())
            .reduce(0L, Long::sum);

        if (voucher.isPresent()) { // 바우처 있을 때
            totalPrice = voucher.get().discount(totalPrice);
        }

        return totalPrice;

    }

    public UUID getOrderId() {
        return orderId;
    }
}
