package org.voucherProject.voucherProject.order.repository;

import org.voucherProject.voucherProject.order.entity.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID orderId);
}
