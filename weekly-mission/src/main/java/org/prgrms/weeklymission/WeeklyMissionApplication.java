package org.prgrms.weeklymission;

import org.prgrms.weeklymission.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeeklyMissionApplication {
	public static void main(String[] args) {
		var springApplication = new SpringApplication(WeeklyMissionApplication.class);
		springApplication.setAdditionalProfiles("dev");
		var applicationContext = springApplication.run(args);
		var app = applicationContext.getBean(Application.class);
		app.run();
	}
}
