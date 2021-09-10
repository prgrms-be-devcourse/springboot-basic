package org.prgrms.kdt.service;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Long customerId, List<OrderItem> orderItems, Long voucherId) {
        var voucher = voucherService.findVoucher(voucherId);
        var order = new Order(new RandomDataGenerator().nextLong(0, 10000), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
