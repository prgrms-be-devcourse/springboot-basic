package com.programmers.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ConfigurationPropertiesScan
public class VoucherManagementApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(VoucherManagementApplication.class);
		application.setAdditionalProfiles("file");
		application.run(args);
	}

}
