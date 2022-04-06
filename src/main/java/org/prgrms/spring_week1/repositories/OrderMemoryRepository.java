package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderMemoryRepository implements OrderRepository{
    List<Order> orderList = new ArrayList<>();

    public OrderMemoryRepository(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public void insert(Order order) {
        orderList.add(order);
    }
}
