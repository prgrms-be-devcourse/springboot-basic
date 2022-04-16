package org.voucherProject.voucherProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class VoucherProjectApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(VoucherProjectApplication.class, args);
		ac.getBean(VoucherEnrollSystem.class).run();
	}
}
