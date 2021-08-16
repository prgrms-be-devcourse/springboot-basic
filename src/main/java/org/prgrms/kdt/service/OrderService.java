package org.prgrms.kdt.service;

import org.prgrms.kdt.ValueObject.OrderItem;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepository orderRepository;


    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList) {
        var order = new Order(UUID.randomUUID(), customerId, orderItemList);
        orderRepository.insert(order);
        // 이미 썼다는걸 저장
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItemList, voucher);
        orderRepository.insert(order);
        // 이미 썼다는걸 저장
        voucherService.userVoucher(voucher);
        return order;
    }

    public void saveOrder() {

    }
}
