package com.programmers.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ConfigurationPropertiesScan
public class VoucherManagementApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(VoucherManagementApplication.class);
		application.setAdditionalProfiles("file");
		setApplicationMode(application);
		application.run(args);
	}

	private static void setApplicationMode(SpringApplication application) {
		if (isWebMode(application)) {
			return;
		}
		application.setWebApplicationType(WebApplicationType.NONE);
	}

	private static boolean isWebMode(SpringApplication application) {
		return application.getAdditionalProfiles().contains("web");
	}

}
