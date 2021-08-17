package org.prgrms.kdt;

import java.util.List;
import java.util.UUID;

// 의존 역전을 사용해 주문정보를 대신 만드는 클래스
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    // 바우처 없을때
    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    // 바우처 있을때
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }

}
