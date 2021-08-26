package org.prgrms.orderApp.domain.order.service;

import org.prgrms.orderApp.domain.order.model.Order;
import org.prgrms.orderApp.domain.order.model.OrderItem;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private OrderRepository orderRepository;


    public Order createOrder(UUID customerId, List<OrderItem> orderItems) throws IOException {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) throws IOException {
        Voucher voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
    public Map<UUID,Order> getAllOrders(){
        return orderRepository.selectAll();

    }
}
