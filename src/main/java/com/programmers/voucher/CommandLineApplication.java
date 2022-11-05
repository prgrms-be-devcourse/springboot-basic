package com.programmers.voucher;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.controller.VoucherController;

public class CommandLineApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.getEnvironment().setActiveProfiles("dev");
		applicationContext.register(AppConfig.class);
		applicationContext.refresh();
		applicationContext.getBean(VoucherController.class).run();
	}
}
