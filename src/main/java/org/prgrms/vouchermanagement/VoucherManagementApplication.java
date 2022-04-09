package org.prgrms.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagementApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(VoucherManagementApplication.class);
		springApplication.setAdditionalProfiles("local"); // Active Profile 설정
		springApplication.run(args);
	}

}
