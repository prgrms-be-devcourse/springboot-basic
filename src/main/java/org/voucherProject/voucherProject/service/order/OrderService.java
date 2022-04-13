package org.voucherProject.voucherProject.service.order;

import org.voucherProject.voucherProject.entity.order.Order;
import org.voucherProject.voucherProject.entity.order.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(UUID userId, List<OrderItem> orderItems);

    Order createOrder(UUID userId, List<OrderItem> orderItems, UUID voucherId);

    Order cancelOrder(UUID orderId);

}
