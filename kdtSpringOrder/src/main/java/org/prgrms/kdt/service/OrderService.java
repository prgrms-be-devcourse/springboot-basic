package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

//바우처 서비스와 오더에 대한 정보를 기록하고 조회할 수 있는 레포지토리에 대해서 의존성을 가진다.
//어떤 레포지토리를 사용할지 선택하지 않고 바우처서비스또한 직접생성하지 않음.
//객체를 만드는 제어권을 OrderContext한테 넘김 => IoC
//IoC가 일어나는 장소를 컨테이너라고 하고, OrderContext는 일종의 IoC라고 할 수 있다.
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherService.useVoucher(voucher);
        return order;
    }

}