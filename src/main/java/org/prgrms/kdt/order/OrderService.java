package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.VoucherService;
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
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        // 바우처아이디를 가져오는데 해당 바우처에대한 정보를 getVoucher를 통해 VoucherRepository에서 가져와야한. 없으면 RuntimeException 발생
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        // 주문이 들어오게되면 VoucherService를 통해서 주문을 생성하고 해당 값을 orderRepository에 insert함으로서
        // 영속성을 갖도록 하고 바우처의 중복을 방지하기위해 voucherService에서 useVoucher 메소드를 통해 처리한다
        // 그 후에 order객체를 반환
        return order;
    }
}
