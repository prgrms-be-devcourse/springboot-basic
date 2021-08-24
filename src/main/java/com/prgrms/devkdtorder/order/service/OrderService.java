package com.prgrms.devkdtorder.order.service;

import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.service.VoucherService;
import com.prgrms.devkdtorder.order.domain.Order;
import com.prgrms.devkdtorder.order.domain.OrderItem;
import com.prgrms.devkdtorder.order.repository.OrderRepository;
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
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
