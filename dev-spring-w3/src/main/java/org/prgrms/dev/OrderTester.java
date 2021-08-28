package org.prgrms.dev;

import org.prgrms.dev.config.AppConfiguration;
import org.prgrms.dev.order.domain.OrderItem;
import org.prgrms.dev.order.service.OrderService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class OrderTester {
	public static void main(String[] args) {

		var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
		var customerId = UUID.randomUUID();
		var orderService = applicationContext.getBean(OrderService.class);
		var order = orderService.createOrder(customerId,
				new ArrayList<OrderItem>(List.of(new OrderItem(UUID.randomUUID(), 100L, 1))));
		Assert.isTrue(order.totalAmount() == 100L,
				MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));
	}
}