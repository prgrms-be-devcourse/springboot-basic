package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;

@SpringBootApplication
@ComponentScan(
		basePackages = {"org.prgrms.kdt.voucher",
		"org.prgrms.kdt.order",
		"org.prgrms.kdt.configuration"}
)
public class KdtApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("dev");
		var applicationContext = springApplication.run(args);

		var orderProperties = applicationContext.getBean(OrderProperties.class);
		System.out.println("version -> " + orderProperties.getVersion() );
		System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
		System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
		System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

	}

}
