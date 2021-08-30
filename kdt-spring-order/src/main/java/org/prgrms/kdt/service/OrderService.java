package org.prgrms.kdt.service;

import org.prgrms.kdt.repository.order.OrderRepository;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.voucher.Voucher;
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

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher); // voucher 사용 표시
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }
}
