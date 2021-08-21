package org.prgrms.orderApp.repository;

import org.prgrms.orderApp.model.order.Order;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    public Map<UUID, Order> selectAll();
    public Order insert(Order order);
    public Optional<Order> selectById(UUID orderId);

}
