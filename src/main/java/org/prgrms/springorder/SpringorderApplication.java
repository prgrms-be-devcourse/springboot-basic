package org.prgrms.springorder;

import org.prgrms.springorder.controller.CommandLineApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("org.prgrms.springorder.properties")
@SpringBootApplication
public class SpringorderApplication implements CommandLineRunner {

	private final CommandLineApp commandLineApp;

	public SpringorderApplication(CommandLineApp voucherController) {
		this.commandLineApp = voucherController;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringorderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		commandLineApp.run();
	}
}

