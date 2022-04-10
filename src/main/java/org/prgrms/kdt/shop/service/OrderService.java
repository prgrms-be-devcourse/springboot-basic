package org.prgrms.kdt.shop.service;

import org.prgrms.kdt.shop.domain.Order;
import org.prgrms.kdt.shop.domain.OrderItem;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList) {
        Order order = new Order.Builder(UUID.randomUUID(), customerId, orderItemList).build();
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList, UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);
        Order order = new Order.Builder(UUID.randomUUID(), customerId, orderItemList).setVoucher(voucher).build();
        orderRepository.insert(order);
        return order;
    }

}
