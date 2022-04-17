package com.prgrms.vouchermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.prgrms.vouchermanagement.voucher.VoucherController;

@SpringBootApplication
public class VoucherManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherManagementApplication.class,
			args);

		VoucherController runner = applicationContext.getBean(VoucherController.class);

		runner.run();
	}

}
