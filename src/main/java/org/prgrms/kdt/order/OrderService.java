package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

public class OrderService { // 바우처 서비스와 오더에 대한 레포지토리와 의존성을 갖을 것
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    /**
     * 바우처 없는 order 생성
     */
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        //voucherService.useVoucher(voucher);
        return order;
    }
}
