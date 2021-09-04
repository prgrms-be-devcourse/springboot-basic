package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
		basePackages = {"org.prgrms.kdt.customer", "org.prgrms.kdt.voucher", "org.prgrms.kdt.order", "org.prgrms.kdt.configuration"}
)
public class KdtApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("dev");
		var applicationContext = SpringApplication.run(KdtApplication.class, args);

	}

}
