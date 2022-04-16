package com.prgrms.kdt.springbootbasic;

import com.prgrms.kdt.springbootbasic.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringbootBasicApplication {
	private static VoucherController voucherController;

	public static void main(String[] args) throws IOException {
		var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
		var controller = applicationContext.getBean(VoucherController.class);
		controller.runVoucherProgramInOrder();
	}

}
