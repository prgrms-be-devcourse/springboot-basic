package com.programmers.kdtspringorder.order;

import com.programmers.kdtspringorder.voucher.VoucherService;
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

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList, UUID voucherId) {
        var voucher = voucherService.findByID(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItemList, voucher);
        voucherService.useVoucher(voucher);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }
}
