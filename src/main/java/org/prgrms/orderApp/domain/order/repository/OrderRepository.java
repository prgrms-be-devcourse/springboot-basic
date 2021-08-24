package org.prgrms.orderApp.domain.order.repository;

import org.prgrms.orderApp.domain.order.model.Order;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    public Map<UUID, Order> selectAll();
    public Order insert(Order order) throws IOException;
    public Optional<Order> selectById(UUID orderId);

}
