package com.prgrms.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherManagementApplication.class,
			args);

		CommandLineApplication runner = applicationContext.getBean(CommandLineApplication.class);

		runner.run();
	}

}
