package org.prgrms.springbasic;

import org.prgrms.springbasic.config.AppConfig;
import org.prgrms.springbasic.domain.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(AppConfig.class);
		springApplication.setAdditionalProfiles("dev");
		var applicationContext = springApplication.run(args);
		var app = applicationContext.getBean(Application.class);
		app.run();
	}
}
