package org.prgrms.kdt.order;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:51 오후
 */
@Service
@Profile("dev")
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        return new Order(UUID.randomUUID(), customerId, orderItems);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        if (orderItems.isEmpty()) {
            throw new RuntimeException("orderItems are empty!");
        }
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }

}