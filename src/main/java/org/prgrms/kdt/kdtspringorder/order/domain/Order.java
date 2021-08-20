package org.prgrms.kdt.kdtspringorder.order.domain;

import org.prgrms.kdt.kdtspringorder.common.enums.OrderStatus;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 주문
 */
public class Order {

    private final UUID orderId; // 주문 ID
    private final UUID customerId; // 고객 ID
    private final List<OrderItem> orderItems; // 주문 상품 리스트
    private Optional<Voucher> voucher; // 바우처
    private OrderStatus orderStatus = OrderStatus.ACCEPTED; // 주문 상태

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    /**
     * 할인률을 적용한 총 주문 금액을 계산합니다.
     * @return 계산된 주문 금액을 반환합니다.
     */
    public long totalAmount() {

        Long beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);

    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
