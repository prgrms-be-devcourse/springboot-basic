package org.prgrms.kdtspringorder.order.service;

import org.prgrms.kdtspringorder.order.domain.implementation.Order;
import org.prgrms.kdtspringorder.order.domain.implementation.OrderItem;
import org.prgrms.kdtspringorder.order.repository.abstraction.OrderRepository;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;

public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList){
        Order order = new Order(UUID.randomUUID(),customerId, orderItemList);
        orderRepository.insert(order);
        return order;
    }
}
