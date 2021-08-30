package org.prgms.order;

import org.prgms.voucher.VoucherService;
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

    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        var order =  new Order(UUID.randomUUID(),customerId,orderItems); // order 서비스를 만든다
        return orderRepository.insert(order);
    }


    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId); //voucherId를 통해 voucher를 가져온다.
        var order =  new Order(UUID.randomUUID(),customerId,orderItems,voucher); // order 서비스를 만든다
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
