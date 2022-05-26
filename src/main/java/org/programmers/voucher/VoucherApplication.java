package org.programmers.voucher;

import org.programmers.voucher.controller.VoucherRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(VoucherApplication.class, args);
		VoucherRunner voucherRunner = ac.getBean(VoucherRunner.class);
		voucherRunner.run();
	}
}
