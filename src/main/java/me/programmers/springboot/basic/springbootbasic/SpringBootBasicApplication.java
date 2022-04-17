package me.programmers.springboot.basic.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class SpringBootBasicApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SpringBootBasicApplication.class);
		springApplication.setAdditionalProfiles("default");
		ConfigurableApplicationContext context = springApplication.run(args);

		showBlackList(context);

		CommandLineApplication app = context.getBean(CommandLineApplication.class);
		ApplicationController controller = new ApplicationController(app);
		controller.runCommandLineApp();
	}

	private static void showBlackList(ConfigurableApplicationContext context) {
		try {
			Resource resource = context.getResource("file:customer_blacklist.csv");
			List<String> blackList = Files.readAllLines(resource.getFile().toPath());
			System.out.println("customer blacklist " + blackList.stream().reduce("", (a, b) -> a + "\n" + b));
		} catch (IOException e) {

		}

	}
}
