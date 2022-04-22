package org.voucherProject.voucherProject.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voucherProject.voucherProject.order.entity.Order;
import org.voucherProject.voucherProject.order.entity.OrderItem;
import org.voucherProject.voucherProject.voucher.entity.Discountable;
import org.voucherProject.voucherProject.order.repository.OrderRepository;
import org.voucherProject.voucherProject.voucher.service.VoucherService;

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
    public Order createOrder(UUID userId, List<OrderItem> orderItems, UUID voucherId){
        Voucher voucher = voucherService.findById(voucherId);
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

