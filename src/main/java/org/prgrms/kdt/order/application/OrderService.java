package org.prgrms.kdt.order.application;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }
    
}
