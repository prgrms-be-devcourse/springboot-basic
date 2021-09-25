package com.prgrms.w3springboot.order;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.order.service.OrderService;

class OrderTest {

	@DisplayName("의존성 주입으로 주문 생성 테스트")
	@Test
	void testCreateOrderByDI() {
		var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		var orderService = applicationContext.getBean(OrderService.class);
		var order = orderService.createOrder(UUID.randomUUID(), List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1))
		);

		assertThat(order.totalAmount()).isEqualTo(100L);
	}

}