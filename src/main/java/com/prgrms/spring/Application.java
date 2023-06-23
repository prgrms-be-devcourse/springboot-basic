package com.prgrms.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(Application.class);
//		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
//		SpringApplication.run(Application.class, args);
		var applicationContext = springApplication.run(args);
		applicationContext.getBean(AppRunner.class).run();
	}

}
