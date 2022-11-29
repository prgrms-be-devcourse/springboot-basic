package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.global.config.VoucherProperties;
import org.prgrms.voucherapplication.domain.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {VoucherProperties.class})
@SpringBootApplication
public class VoucherApplication {

	private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

	public static void main(String[] args) {
		try {
			ApplicationContext applicationContext = new SpringApplication(VoucherApplication.class).run(args);

			VoucherController voucherController = applicationContext.getBean(VoucherController.class);
			voucherController.start();
		} catch (Exception e) {
			logger.error("애플리케이션 실행 오류 : {}", e.getMessage());
		}
	}
}
