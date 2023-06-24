package org.weekly.weekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.voucher.VoucherController;
import org.weekly.weekly.voucher.service.VoucherService;

import java.util.Scanner;

@SpringBootApplication
public class VoucherManageApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VoucherManageApplication.class, args);
		context.getBean(VoucherController.class).start();
	}

}