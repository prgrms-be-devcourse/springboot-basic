package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
	basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order", "org.prgrms.kdt.configuration"}
)
public class KdtApplication {

	private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);

	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(KdtApplication.class, args);
		var orderProperties = applicationContext.getBean(OrderProperties.class);
		logger.error("logger name => {}", logger.getName());
		logger.warn("version -> {}", orderProperties.getVersion());
		logger.warn("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
		logger.warn("supportVendors -> {}", orderProperties.getSupportVendors());
		logger.warn("description -> {}", orderProperties.getDescription());
	}

}
