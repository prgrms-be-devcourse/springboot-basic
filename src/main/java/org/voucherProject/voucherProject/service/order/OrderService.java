package org.voucherProject.voucherProject.service.order;

import org.voucherProject.voucherProject.entity.order.Order;
import org.voucherProject.voucherProject.entity.order.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    /**
     * 오버로딩를 쓰는게 좋은가?
     * 혹은 메소드명을 다르게하여 ex) createOrderVoucher
     * 타인이 보았을 때 그 역할을 분명히 하는게 좋은 것인가?
     */
    Order createOrder(UUID userId, List<OrderItem> orderItems);

    Order createOrder(UUID userId, List<OrderItem> orderItems, UUID voucherId);

    Order cancelOrder(UUID orderId);

}
