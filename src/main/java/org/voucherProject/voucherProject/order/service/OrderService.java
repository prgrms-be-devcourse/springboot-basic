package org.voucherProject.voucherProject.order.service;

import org.voucherProject.voucherProject.order.entity.Order;
import org.voucherProject.voucherProject.order.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(UUID userId, List<OrderItem> orderItems);

    Order createOrder(UUID userId, List<OrderItem> orderItems, UUID voucherId);

    Order cancelOrder(UUID orderId);

}
