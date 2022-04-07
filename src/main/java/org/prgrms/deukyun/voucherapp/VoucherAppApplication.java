package org.prgrms.deukyun.voucherapp;

import org.prgrms.deukyun.voucherapp.entity.FixedAmountVoucher;
import org.prgrms.deukyun.voucherapp.entity.Order;
import org.prgrms.deukyun.voucherapp.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.entity.Voucher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
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
		List<OrderItem> orderItems = new ArrayList<>() {{
			add(new OrderItem(UUID.randomUUID(), 100L, 1));
		}};

		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

		Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);

		Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0}", order.totalAmount()));
	}
}
