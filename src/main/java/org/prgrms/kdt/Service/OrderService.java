package org.prgrms.kdt.Service;

import org.prgrms.kdt.Model.Order;
import org.prgrms.kdt.Model.OrderItem;
import org.prgrms.kdt.Repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    //외부에서 주입받을 수 있도록
    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

   //order을 만들 수 있는 메소드
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        return new Order(UUID.randomUUID(), customerId, orderItems);
    }

   //바우처에서 가져옴
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        if (orderItems.isEmpty()) {
            throw new RuntimeException("orderItems are empty!");
        }
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        //레파지토리에서 저장
        orderRepository.save(order);

        //재사용을 못하게 하는
        voucherService.useVoucher(voucher);
        return order;
    }

}
