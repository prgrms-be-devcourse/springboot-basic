package org.programmers.applicationcontext.order;
import java.util.List;
import java.util.UUID;

public class OrderService {
    // private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    /*의존성 주입을 위해 생성자를 만듦
    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }
    */

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems){
        var order =  new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.insert(order); // 만들어진 주문 객체 order를 영속성을 위해 저장하기 위한 코드
        return order;
    }

    /*
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId); // 어떤 바우쳐 종류인지 알아내기 위한 객체
        var order =  new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order); // 만들어진 주문 객체 order를 영속성을 위해 저장하기 위한 코드
        voucherService.useVoucher(voucher); // 바우처를 썼다는 것을 표시하기 위한 코드
        return order;
    }
     */
}
