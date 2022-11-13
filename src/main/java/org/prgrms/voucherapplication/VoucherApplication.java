package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.config.VoucherProperties;
import org.prgrms.voucherapplication.controller.VoucherController;
import org.prgrms.voucherapplication.utils.CsvFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableConfigurationProperties(value = {VoucherProperties.class})
@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(VoucherApplication.class);
		springApplication.setAdditionalProfiles("prod");
		ApplicationContext applicationContext = springApplication.run(args);

		VoucherProperties voucherProperties = applicationContext.getBean(VoucherProperties.class);
		String blacklistFilePath = voucherProperties.getBlacklistFilePath();
		Resource resource = applicationContext.getResource(blacklistFilePath);

		CsvFile csvfile = applicationContext.getBean(CsvFile.class);
		String blacklist = csvfile.readFileLines(resource);

		VoucherController voucherController = applicationContext.getBean(VoucherController.class);
		voucherController.init(blacklist);
		voucherController.start();
	}

}
