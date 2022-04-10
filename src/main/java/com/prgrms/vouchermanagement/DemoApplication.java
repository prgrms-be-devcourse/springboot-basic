package com.prgrms.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
		ac.getBean(VoucherManagement.class).run();
	}

}
