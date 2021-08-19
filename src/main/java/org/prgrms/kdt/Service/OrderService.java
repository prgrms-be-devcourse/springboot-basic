package org.prgrms.kdt.Service;

import org.prgrms.kdt.Entity.Order;
import org.prgrms.kdt.VO.OrderItem;
import org.prgrms.kdt.Repository.OrderRepository;

import java.util.List;
import java.util.UUID;

// voucher service를 이용, order 정보를 기록/조회하는 repo에 의존
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;


    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    //Order 생성
    // voucher 없이 Order하기
    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        // Order 저장
        orderRepository.insert(order);
        // voucher 사용했음.
        return order;
    }
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        // Order 저장
        orderRepository.insert(order);
        // voucher 사용했음.
        voucherService.useVoucher(voucher);
        return order;
    }
}
