package org.prgrms.dev.order.service;

import org.prgrms.dev.order.repository.OrderRepository;
import org.prgrms.dev.order.domain.Order;
import org.prgrms.dev.order.domain.OrderItem;
import org.prgrms.dev.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

/**
 *  - 비즈니스 로직을 담는 클래스
 *  - OrderService는 'Voucher Service'와
 *      'Order에 대한 정보를 기록하고 조회할 수 있는 repository'에 대하여 의존성을 갖는다.
 */
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.create(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.create(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
