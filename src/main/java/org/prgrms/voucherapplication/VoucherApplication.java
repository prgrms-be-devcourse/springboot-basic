package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.config.VoucherProperties;
import org.prgrms.voucherapplication.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {VoucherProperties.class})
@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplication(VoucherApplication.class).run(args);

		VoucherController voucherController = applicationContext.getBean(VoucherController.class);
		voucherController.start();
	}

}
