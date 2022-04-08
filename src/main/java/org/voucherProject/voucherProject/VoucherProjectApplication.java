package org.voucherProject.voucherProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoucherProjectApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(VoucherProjectApplication.class, args);
		ac.getBean(VoucherEnrollSystem.class).run();
	}

}
