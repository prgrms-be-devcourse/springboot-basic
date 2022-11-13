package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.common.VoucherException;
import org.prgrms.voucherapplication.config.VoucherProperties;
import org.prgrms.voucherapplication.controller.VoucherController;
import org.prgrms.voucherapplication.utils.CsvFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

import static org.prgrms.voucherapplication.utils.CsvFile.FILE_ERROR;

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

		File file;
		try {
			file = resource.getFile();
		} catch (IOException e) {
			throw new VoucherException(FILE_ERROR);
		}

		CsvFile csvfile = applicationContext.getBean(CsvFile.class);
		String blacklist = csvfile.readFileLines(file);

		VoucherController voucherController = applicationContext.getBean(VoucherController.class);
		voucherController.init(blacklist);
		voucherController.start();
	}

}
