package org.prgrms.kdt;

import org.prgrms.kdt.application.cmd.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext
				= SpringApplication.run(KdtApplication.class, args);

		applicationContext.getBean(VoucherController.class).start();

		applicationContext.close();
	}
}
