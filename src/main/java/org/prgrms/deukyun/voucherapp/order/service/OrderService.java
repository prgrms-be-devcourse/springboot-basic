package org.prgrms.deukyun.voucherapp.order.service;

import org.prgrms.deukyun.voucherapp.order.entity.Order;
import org.prgrms.deukyun.voucherapp.order.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.order.repository.OrderRepository;
import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.voucher.service.VoucherService;
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

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
