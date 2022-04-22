package org.programmers.voucher;

import org.programmers.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(VoucherApplication.class, args);
		VoucherController voucherController = ac.getBean(VoucherController.class);
		voucherController.run();
	}
}
