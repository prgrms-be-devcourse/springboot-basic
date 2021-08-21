package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.order.OrderProperties;
import org.prgrms.kdtspringdemo.voucher.JdbcVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.order", "org.prgrms.kdtspringdemo.voucher", "org.prgrms.kdtspringdemo.configuration"})
public class KdtSpringDemoApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(KdtSpringDemoApplication.class);
		springApplication.setAdditionalProfiles("local");
		var application = springApplication.run(args);
		var orderProperties = application.getBean(OrderProperties.class);
		System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
		System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
		System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
		System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

		var voucherRepository = application.getBean(VoucherRepository.class);

		System.out.println(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository instanceof JdbcVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository.getClass().getCanonicalName()));
	}

}
