package org.prgms.order.order.repository;

import org.prgms.order.order.entity.Order;

public class FileOrderRepository implements OrderRepository{
    static final String filePath = "classpath:order_customer";
    @Override
    public Order insert(Order order) {
        return null;
    }
}
