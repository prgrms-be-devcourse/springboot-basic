package org.voucherProject.voucherProject.repository;

import org.voucherProject.voucherProject.entity.order.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID orderId);
}
