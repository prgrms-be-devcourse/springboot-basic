package org.prgrms.kdtspringorder.order.domain.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdtspringorder.order.enums.OrderStatus;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;

public class Order {

  private final UUID orderId;
  private final UUID customerId;
  private final List<OrderItem> orderItems;
  private final Optional<Voucher> voucher;
  private final long ZERO = 0L;
  private OrderStatus orderStatus;

  public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, OrderStatus orderStatus) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.orderItems = orderItems;
    this.voucher = Optional.empty();
  }

  public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher,
      OrderStatus orderStatus) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.orderItems = orderItems;
    this.voucher = Optional.of(voucher);
  }

  public long totalAmount() {
    long beforeDiscount = orderItems
        .stream()
        .map(v -> v.getProductPrice() * v.getQuantity())
        .reduce(ZERO, Long::sum);

    return voucher
        .map(value -> value.discount(beforeDiscount))
        .orElse(beforeDiscount);
  }

  public void changeOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}
