package org.prgrms.kdt.order;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/22 Time: 3:41 오후
 */
@Qualifier("memory")
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(Order order) {
        storage.put(order.getOrderId(), order);
        return storage.get(order.getOrderId());
    }
}
