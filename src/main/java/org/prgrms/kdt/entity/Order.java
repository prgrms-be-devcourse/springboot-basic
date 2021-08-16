package org.prgrms.kdt.entity;

import org.prgrms.kdt.ValueObject.OrderItem;
import org.prgrms.kdt.model.OrderStatus;

import java.util.List;
import java.util.UUID;

// voucher라는 인터페이스를 두기전에는 fixedamount에 굉장히 의존적인 코드다.
public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    // 인터페이스를 둠으로써 퍼센트바우처인지 그냥 바우처인지 신경쓰지 않아도 되게됨
    private Voucher voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처객체를 주입받게됨
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = voucher;
    }

    public long totalAmount() {
        // 주문한 목록을 순회하면서 해당아이템의 가격과 개수를 곱해서 총합 = 토탈어마운트
        var beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        // fixedAmountVoucher가 가진 로직에 의해서 최종값이 나오게됨
        // voucher라는 인터페이스를 통해서 """그냥 바우처에의해 할인이 된다!!! 끝!"""

        return voucher.discount(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
