package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Order;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    void insert(Order order);
    List<Order> orders = new ArrayList<>();
}
