package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {


    private final VoucherService voucherService;
    private final OrderRepository orderRepository;


    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList) {
        var order = new Order(UUID.randomUUID(), customerId, orderItemList);
        orderRepository.insert(order);
        // 이미 썼다는걸 저장
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItemList, UUID voucherId) throws Throwable {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItemList, voucher);
        orderRepository.insert(order);
        // 이미 썼다는걸 저장
        voucherService.userVoucher(voucher);
        return order;
    }

    public void saveOrder() {

    }
}
