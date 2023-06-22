package com.prgms.springbootbasic;

import com.prgms.springbootbasic.controller.VoucherController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
	
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		VoucherController commandLineController = ac.getBean(VoucherController.class);
		while(commandLineController.run());
	}
	
}
