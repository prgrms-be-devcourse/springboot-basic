package org.prgrms.deukyun.voucherapp.order.service;

import org.prgrms.deukyun.voucherapp.order.entity.Order;
import org.prgrms.deukyun.voucherapp.order.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.order.repository.OrderRepository;
import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 주문 서비스
 */
@Service
public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    /**
     * 주문 생성 바우처가 없는 경우
     *
     * @param customerId
     * @param orderItems
     * @return 주문객체
     */
    public Order createOrderWithoutVoucher(UUID customerId, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .customerId(customerId)
                .orderItems(orderItems)
                .build();

        return insert(order);
    }

    /**
     * 주문 생성 바우처를 가진 경우
     *
     * @param customerId
     * @param orderItems
     * @param voucherId
     * @return 주문객체
     */
    public Order createOrderWithVoucher(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = Order.builder()
                .customerId(customerId)
                .orderItems(orderItems)
                .voucher(voucher)
                .build();

        return insert(order);
    }

    private Order insert(Order order) {
        return orderRepository.insert(order);
    }
}
