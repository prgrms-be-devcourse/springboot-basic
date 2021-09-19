package org.prgrms.kdt.order.service;

import org.prgrms.kdt.order.model.Order;
import org.prgrms.kdt.order.model.OrderItem;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
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
