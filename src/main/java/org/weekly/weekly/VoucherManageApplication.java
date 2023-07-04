package org.weekly.weekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.weekly.weekly.ui.reader.ScannerWrap;

@SpringBootApplication
public class VoucherManageApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VoucherManageApplication.class, args);
		String read = context.getEnvironment().getProperty("command.read", String.class);
		System.out.println(read);

//		context.getBean(VoucherManagementController.class).start();

	}
}