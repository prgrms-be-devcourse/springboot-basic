package org.programmers.kdt.order;

import org.programmers.kdt.voucher.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderSerivce {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderSerivce(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        this.orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = this.voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        this.orderRepository.insert(order);
        this.voucherService.useVoucher(voucher);
        return order;
    }
}
