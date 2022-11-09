package org.prgrms.springorder;

import org.prgrms.springorder.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringorderApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringorderApplication.class, args);
		applicationContext.getBean(VoucherController.class).run();
	}

}
