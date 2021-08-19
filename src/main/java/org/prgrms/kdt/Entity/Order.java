package org.prgrms.kdt.Entity;
//entity class
//비즈니스 로직 - discount, amount, totalMoney 등

import org.prgrms.kdt.VO.OrderItem;
import org.prgrms.kdt.VO.OrderStatus;
import org.prgrms.kdt.VO.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId; //identifier
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    // private long discountAmount; Class로 정의하여 Order이 discountAmout 객체를 사용하게 하여, 의존성부여
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED; //초기 상태

    // voucher 없이 order
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

    public long totalAmount(){
        var beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

}
