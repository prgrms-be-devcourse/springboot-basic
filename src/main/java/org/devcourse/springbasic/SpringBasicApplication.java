package org.devcourse.springbasic;

import org.devcourse.springbasic.controller.VoucherApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBasicApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(SpringBasicApplication.class, args);
		VoucherApplication voucherApp = ac.getBean(VoucherApplication.class);
		String deviceType = "ConsoleDevice";
		voucherApp.run(deviceType);
	}
}
