package org.prgrms.kdtspringorder.order;

import org.prgrms.kdtspringorder.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID order, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = order;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }
    public Order(UUID order, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = order;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }
    public long totalAmount(){
        var beforeDiscount=orderItems.stream().map(v->v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        if (voucher.isPresent()){
            return voucher.get().discount(beforeDiscount);
        }else{
            return beforeDiscount;
        }
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId(){
        return orderId;
    }
}
