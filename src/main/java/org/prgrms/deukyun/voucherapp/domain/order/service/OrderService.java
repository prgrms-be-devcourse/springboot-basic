package org.prgrms.deukyun.voucherapp.domain.order.service;

import org.prgrms.deukyun.voucherapp.domain.order.entity.Order;
import org.prgrms.deukyun.voucherapp.domain.order.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.domain.order.repository.OrderRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
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
     * @param customerId 고객 ID
     * @param orderItems 주문 아이템 목록
     * @return 주문객체
     */
    public Order createOrderWithNoVoucher(UUID customerId, List<OrderItem> orderItems) {
        return Order.createOrderWithoutVoucher(customerId, orderItems);
    }

    /**
     * 주문 생성 바우처를 가진 경우
     *
     * @param customerId 고객 ID
     * @param orderItems 주문 아이템 목록
     * @param voucherId  바우처 ID
     * @return 주문객체
     */
    public Order createOrderWithVoucher(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = Order.createOrderWithVoucher(customerId, orderItems, voucher);
        orderRepository.insert(order);
        return order;
    }
}
