package org.prgrms.orderapp.service;

import org.prgrms.orderapp.model.Order;
import org.prgrms.orderapp.model.OrderItem;
import org.prgrms.orderapp.repository.OrderRepository;
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
        return new Order(UUID.randomUUID(), customerId, orderItems);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        if (orderItems.isEmpty()) {
            throw new RuntimeException("orderItems are empty!");
        }
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.save(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
