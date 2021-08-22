package org.prgrms.kdt.order.service;

import org.prgrms.kdt.configuration.VersionProvider;
import org.prgrms.kdt.order.domain.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 자신이 직접 어떠한 OrderRepository를 쓸지 선택하지 않고 OrderRepository ,VoucherService 또한 직접 생성하지 않음.
 * Instance(객체)를 만드는 제어권을 OrderContext에 넘긴 겨.
 * - 이는 IoC가 일어나게 된 것.
 * - 이러한 IoC가 가능하게 하는 공간/장소는 IoC Container라고 부름
 * - OrderContext = IoC Container
 *
 */
// SteropType Annotation인 Service를 달아주면 됩니다.
@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;
    private final VersionProvider versionProvider;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository, VersionProvider versionProvider) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
        this.versionProvider = versionProvider;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        versionProvider.getVersion() // immutable 보장가능
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);

        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);
    }
}
