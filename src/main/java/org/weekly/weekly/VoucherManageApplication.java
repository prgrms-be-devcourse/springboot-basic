package org.weekly.weekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherManageApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VoucherManageApplication.class, args);
		context.getBean(VoucherManagementController.class).start();
	}
}