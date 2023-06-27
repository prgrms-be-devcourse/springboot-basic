package com.prgrms.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(Application.class);
		var applicationContext = springApplication.run(args);
		applicationContext.getBean(AppRunner.class).run();
	}

}
