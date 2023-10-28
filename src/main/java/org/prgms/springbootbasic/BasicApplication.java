package org.prgms.springbootbasic;

import org.prgms.springbootbasic.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BasicApplication.class, args);
		var mainController = applicationContext.getBean(MainController.class);

		mainController.run();
	}

}
