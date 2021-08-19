package org.prms.service;

import org.prms.controller.Order;
import org.prms.domain.OrderItem;
import org.prms.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    // Voucher가 없는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {

        var order= new Order(UUID.randomUUID(),customerId,orderItems);
        // order 정보 저장
        orderRepository.insert(order);

        return order;
    }


    // Voucher가 잇는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher=voucherService.getVoucher(voucherId);
        var order= new Order(UUID.randomUUID(),customerId,orderItems,voucher);
        // order 정보 저장
        orderRepository.insert(order);

        // voucher를 재사용못하게 하는 useVoucher를 만듦
        voucherService.useVoucher(voucher);

        return order;
    }


}
