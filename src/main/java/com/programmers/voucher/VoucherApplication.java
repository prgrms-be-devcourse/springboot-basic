package com.programmers.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.programmers.voucher.console.ConsoleVoucherApp;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
		applicationContext.getBean(ConsoleVoucherApp.class).run();
	}
}
