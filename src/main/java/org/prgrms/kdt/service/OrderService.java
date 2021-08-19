package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Order;
import org.prgrms.kdt.domain.OrderItem;
import org.prgrms.kdt.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
//        TODO
//        voucherService.useVoucher(voucher);
        return orderRepository.insert(order);
    }
}
