package org.prgrms.kdtbespring.order;

import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// 오더 서비스는 바우처와 오더에 대해서 기록하고 조회할 수 있는 레포지토리에 대해서 의존성을 가짐
@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    // 오더 서비스는 오더 객체의 생성에 대한 책임을 갖게 됨
    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        // 오더에 대한 저장
        return orderRepository.insert(order);
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        Voucher voucher = voucherService.getVoucher(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        // 오더에 대한 저장
        orderRepository.insert(order);
        // 바우처 사용 (다시 사용 여부 저장)
        voucherService.useVoucher(voucher);
        return order;
    }
}
