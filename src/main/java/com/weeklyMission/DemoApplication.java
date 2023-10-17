package com.weeklyMission;

import com.weeklyMission.exception.ExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		ExceptionHandler handler = applicationContext.getBean(ExceptionHandler.class);

		while(true){
			handler.run();
		}
	}
}
