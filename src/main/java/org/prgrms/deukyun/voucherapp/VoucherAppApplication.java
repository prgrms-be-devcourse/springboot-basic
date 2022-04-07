package org.prgrms.deukyun.voucherapp;

import org.prgrms.deukyun.voucherapp.context.OrderContext;
import org.prgrms.deukyun.voucherapp.entity.FixedAmountVoucher;
import org.prgrms.deukyun.voucherapp.entity.Order;
import org.prgrms.deukyun.voucherapp.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.entity.Voucher;
import org.prgrms.deukyun.voucherapp.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class VoucherAppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(VoucherAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UUID customerId = UUID.randomUUID();

		OrderContext orderContext = new OrderContext();
		OrderService orderService = orderContext.orderService();

		List<OrderItem> orderItems = Arrays.asList(new OrderItem(UUID.randomUUID(), 100L, 1));
		Order order = orderService.createOrder(customerId, orderItems);

		Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount {0}", order.totalAmount()));
	}
}
