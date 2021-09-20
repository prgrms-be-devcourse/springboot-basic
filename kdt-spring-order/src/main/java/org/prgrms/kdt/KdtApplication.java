package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class KdtApplication {
	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("jdbc");
		springApplication.run(args);
	}
}
