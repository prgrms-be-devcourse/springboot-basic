package org.prgrms.kdtspringorder.order.service;

import org.prgrms.kdtspringorder.order.domain.implementation.Order;
import org.prgrms.kdtspringorder.order.domain.implementation.OrderItem;
import org.prgrms.kdtspringorder.order.enums.OrderStatus;
import org.prgrms.kdtspringorder.order.repository.abstraction.OrderRepository;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList){
        Order order = new Order(UUID.randomUUID(), customerId, orderItemList, OrderStatus.ACCEPTED);
        orderRepository.insert(order);
        return order;
    }
}
