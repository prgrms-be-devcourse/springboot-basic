package com.prgrms.w3springboot.order;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.order.service.OrderService;
import com.prgrms.w3springboot.voucher.FixedAmountVoucher;
import com.prgrms.w3springboot.voucher.PercentAmountVoucher;

class OrderTest {

	@Test
	@DisplayName("고정 할인 바우처 생성 테스트")
	void testCreateFixedVoucher() {
		var customerId = UUID.randomUUID();
		var orderItems = List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1)
		);
		var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
		var order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

		assertThat(order.totalAmount()).isEqualTo(90L);
	}

	@Test
	@DisplayName("퍼센트 할인 바우처 생성 테스트")
	void testCreatePercentVoucher() {
		var customerId = UUID.randomUUID();
		var orderItems = List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1)
		);
		var percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), 200L);
		var order = new Order(UUID.randomUUID(), customerId, orderItems, percentAmountVoucher);

		assertThat(order.totalAmount()).isEqualTo(200L);
	}

	@Test
	@DisplayName("의존성 빈으로 주문 생성 테스트")
	void testCreateOrderByDI() {
		var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		var orderService = applicationContext.getBean(OrderService.class);
		var order = orderService.createOrder(UUID.randomUUID(), List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1))
		);

		assertThat(order.totalAmount()).isEqualTo(100L);
	}

}