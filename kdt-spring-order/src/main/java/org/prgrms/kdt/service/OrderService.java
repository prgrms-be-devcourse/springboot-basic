package org.prgrms.kdt.service;

import org.prgrms.kdt.configure.Voucher;
import org.prgrms.kdt.entity.Order;
import org.prgrms.kdt.entity.OrderItem;
import org.prgrms.kdt.entity.OrderStatus;
import org.prgrms.kdt.repo.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        return new Order(UUID.randomUUID(),customerId,orderItems);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        if(orderItems.isEmpty()) throw new RuntimeException("orderItems가 비어 있음");

        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(),customerId,orderItems,voucher);
        orderRepository.save(order);
        voucherService.useVoucher(voucher);
        return order;
    }


}
