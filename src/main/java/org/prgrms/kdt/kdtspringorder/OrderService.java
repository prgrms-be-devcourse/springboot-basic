package org.prgrms.kdt.kdtspringorder;

import java.util.List;
import java.util.UUID;

public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepositiry orderRepositiry;

    public OrderService(VoucherService voucherService, OrderRepositiry orderRepositiry) {
        this.voucherService = voucherService;
        this.orderRepositiry = orderRepositiry;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepositiry.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepositiry.insert(order);
        return order;
    }

}
