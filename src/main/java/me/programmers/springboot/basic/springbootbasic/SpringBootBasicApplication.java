package me.programmers.springboot.basic.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootBasicApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SpringBootBasicApplication.class);
		springApplication.setAdditionalProfiles("default");
		ConfigurableApplicationContext context = springApplication.run(args);
		CommandLineApplication app = context.getBean(CommandLineApplication.class);

		ApplicationController controller = new ApplicationController(app);
		controller.runCommandLineApp();
	}


}
