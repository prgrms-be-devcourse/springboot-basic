package com.programmers.commandlind.service;

import com.programmers.commandlind.entity.Order;
import com.programmers.commandlind.entity.OrderItem;
import com.programmers.commandlind.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final VoucherService voucherService;

    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }
}
