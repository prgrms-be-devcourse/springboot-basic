package org.prgrms.kdt.order.service;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

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
