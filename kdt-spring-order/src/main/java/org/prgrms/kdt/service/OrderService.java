package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Order;
import org.prgrms.kdt.domain.OrderItem;
import org.prgrms.kdt.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private final VoucherService voucherService;

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        return new Order(UUID.randomUUID(),customerId,orderItems);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        if(orderItems.isEmpty()) throw new RuntimeException("orderItems가 비어 있음");

        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(),customerId,orderItems,voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }


}
