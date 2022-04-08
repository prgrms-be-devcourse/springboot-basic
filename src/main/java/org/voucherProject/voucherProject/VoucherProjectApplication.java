package org.voucherProject.voucherProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoucherProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoucherProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(VoucherEnrollSystem voucherEnrollSystem) {
		return args -> voucherEnrollSystem.run();
	}

}
