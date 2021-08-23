package org.prgrms.kdt.test;

import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.UUID;


@SpringBootApplication // springboot !
@ComponentScan(basePackages = {"org.prgrms.kdt.*"})//,"org.prgrms.kdt.voucher","org.prgrms.kdt.configuration"})
public class KdtApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);

		springApplication.setAdditionalProfiles("local"); // 코드말고 인텔리제이 환경설정에서 프로파일을 넣어줄 수 있음
		//나중에 jar 패키지로 배포시 Program argument로 전달해서 스프링부트를 돌리면 된다! (--spring.profiles.active=dev)

		var applicationContext= springApplication.run(args);

		//applicationContext가 반환이 된다. 따라서 똑같이 spring 처럼쓰면됨
		//ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);


		var orderProperties = applicationContext.getBean(OrderProperties.class);
		System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
		System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
		System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
		System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

		var voucherRepository = applicationContext.getBean(VoucherRepository.class);
		var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
		// Order
		var customerId = UUID.randomUUID();
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));

	}

}
