package com.programmers.voucher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.config.AppConfig;
import com.programmers.voucher.controller.VoucherController;

public class CommandLineApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		applicationContext.getBean(VoucherController.class).run();
	}
}
