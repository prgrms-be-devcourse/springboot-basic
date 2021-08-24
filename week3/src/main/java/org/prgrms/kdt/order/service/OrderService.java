package org.prgrms.kdt.order.service;

import org.prgrms.kdt.order.model.Order;
import org.prgrms.kdt.order.model.OrderItem;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
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

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList) {
        var order = new Order(UUID.randomUUID(), customerId, orderItemList);
        orderRepository.insert(order);
        return order;
    }

}
