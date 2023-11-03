package org.prgms.springbootbasic;

import org.prgms.springbootbasic.controller.ConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BasicApplication.class, args);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

		if (Arrays.stream(activeProfiles)
				.anyMatch(profile -> profile.equals("dev") || profile.equals("local") || profile.equals("test"))) {
			var mainController = applicationContext.getBean(ConsoleController.class);

			mainController.run();
		}
	}

}
