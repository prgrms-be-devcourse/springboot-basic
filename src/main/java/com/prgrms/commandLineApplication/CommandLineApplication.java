package com.prgrms.commandLineApplication;

import com.prgrms.commandLineApplication.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CommandLineApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
		applicationContext.getBean(VoucherController.class).run();
	}

}
