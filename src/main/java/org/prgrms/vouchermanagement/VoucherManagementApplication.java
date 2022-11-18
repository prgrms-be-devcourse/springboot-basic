package org.prgrms.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(VoucherManagementApplication.class, args);
		ac.getBean(CommandLineApplication.class).run();
	}

}
