package org.voucherProject.voucherProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voucherProject.voucherProject.entity.order.Order;
import org.voucherProject.voucherProject.entity.order.OrderItem;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.repository.OrderRepository;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final VoucherService voucherService;

    @Override
    public Order createOrder(UUID userId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), userId, orderItems);
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(UUID userId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = orderRepository.save(new Order(UUID.randomUUID(), userId, orderItems, voucher));
        voucher.useVoucher();
        return order;
    }

    @Override
    public Order cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancelOrder();
        if (order.getVoucher().isPresent()) {
            order.getVoucher().get().cancelVoucher();
        }
        return order;
    }
}

