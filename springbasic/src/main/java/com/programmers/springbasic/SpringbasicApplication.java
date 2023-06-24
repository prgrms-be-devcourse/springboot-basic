package com.programmers.springbasic;

import com.programmers.springbasic.domain.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbasicApplication {
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new SpringApplication(SpringbasicApplication.class).run(args);

		VoucherController voucherController = applicationContext.getBean(VoucherController.class);
		voucherController.run();
	}
}
