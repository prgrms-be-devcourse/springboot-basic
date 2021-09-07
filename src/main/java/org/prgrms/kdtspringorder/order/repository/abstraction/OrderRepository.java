package org.prgrms.kdtspringorder.order.repository.abstraction;

import org.prgrms.kdtspringorder.order.domain.implementation.Order;

public interface OrderRepository {
  public void insert(Order order);
}
