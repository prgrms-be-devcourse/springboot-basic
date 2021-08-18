package org.prgrms.kdtspringorder.order;

import org.prgrms.kdtspringorder.AppConfigurationClass;
import org.prgrms.kdtspringorder.order.domain.implementation.Order;
import org.prgrms.kdtspringorder.order.domain.implementation.OrderItem;
import org.prgrms.kdtspringorder.order.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        AppConfigurationClass.class);

    UUID customerId = UUID.randomUUID();
    AppConfigurationClass orderContext = new AppConfigurationClass();
    OrderService orderService = applicationContext.getBean(OrderService.class);
    Order order = orderService.createOrder(customerId, new ArrayList<>() {{
      add(new OrderItem(UUID.randomUUID(), 100L, 1));
    }});
    Assert.isTrue(order.totalAmount() == 100L,
        MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
  }
}
