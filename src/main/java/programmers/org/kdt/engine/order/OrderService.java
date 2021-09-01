package programmers.org.kdt.engine.order;

import org.springframework.stereotype.Service;
import programmers.org.kdt.engine.voucher.VoucherService;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, ArrayList<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, ArrayList<OrderItem> orderItems, UUID voucherID) {
        var voucher = voucherService.getVoucher(voucherID);
        var order = new Order(UUID.randomUUID(), customerId, orderItems,voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }
}
