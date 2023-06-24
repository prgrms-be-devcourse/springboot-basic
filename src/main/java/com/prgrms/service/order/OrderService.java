package com.prgrms.service.order;

import lombok.RequiredArgsConstructor;
import org.prgrms.model.order.OrderItem;
import org.prgrms.model.order.Order;
import org.prgrms.repository.order.OrderRepository;
import org.prgrms.service.voucher.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}

