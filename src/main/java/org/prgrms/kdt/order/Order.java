package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// voucher라는 인터페이스를 두기전에는 fixedamount에 굉장히 의존적인 코드다.
public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    // 인터페이스를 둠으로써 퍼센트바우처인지 그냥 바우처인지 신경쓰지 않아도 되게됨
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처객체가 없는경우
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        // 바우처가 없는경우 empty로 처리
        this.voucher = Optional.empty();
    }


    // 바우처객체를 주입받게됨
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);

    }

    public long totalAmount() {
        // 주문한 목록을 순회하면서 해당아이템의 가격과 개수를 곱해서 총합 = 토탈어마운트
        var beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        // voucher객체가 존재할때만 discount메서드통해서 리턴, 없으면 그대로 할인전가격 리턴
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
