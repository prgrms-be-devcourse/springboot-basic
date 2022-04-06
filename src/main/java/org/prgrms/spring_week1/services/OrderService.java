package org.prgrms.spring_week1.services;

import org.prgrms.spring_week1.models.Order;
import org.prgrms.spring_week1.models.OrderItem;
import org.prgrms.spring_week1.models.Voucher;
import org.prgrms.spring_week1.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;
    VoucherService voucherService;

    public OrderService(OrderRepository orderRepository, VoucherService voucherService) {
        this.orderRepository = orderRepository;
        this.voucherService = voucherService;
    }

    // voucher 없는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, Optional.empty());
        orderRepository.insert(order);
        return order;
    }

    // voucher 있는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, Voucher voucher){
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, Optional.of(voucher));
        orderRepository.insert(order);
        return order;
    }
}
