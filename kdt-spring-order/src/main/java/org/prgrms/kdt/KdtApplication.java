package org.prgrms.kdt;

import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderProperties;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(
		basePackages = {
				"org.prgrms.kdt.domain", "org.prgrms.kdt.repository", "org.prgrms.kdt.service",
				"org.prgrms.kdt.Factory", "org.prgrms.kdt.config",
		})
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(KdtApplication.class);
		ConfigurableApplicationContext applicationContext = springApplication.run(args);

		OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
		System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
		System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
		System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
		System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

		ArrayList<OrderItem> orderItems = new ArrayList<>(Arrays.asList(new OrderItem(UUID.randomUUID(), 100, 1)));
		UUID customerId = UUID.randomUUID();
		VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
		Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now()));

		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));
	}

}
