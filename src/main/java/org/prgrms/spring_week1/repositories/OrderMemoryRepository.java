package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMemoryRepository implements OrderRepository{
    List<Order> orderList = new ArrayList<>();

    @Override
    public void insert(Order order) {
        orderList.add(order);
    }
}
