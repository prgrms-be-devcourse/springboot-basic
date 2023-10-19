package com.programmers.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagementApplication {

	public static void main(String[] args) {
		var application = new SpringApplication(VoucherManagementApplication.class);
		application.setAdditionalProfiles("prod");
		application.run(args);
	}

}
