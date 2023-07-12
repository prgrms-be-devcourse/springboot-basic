package org.devcourse.springbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBasicApplication {
    public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(SpringBasicApplication.class, args);
		VoucherAppController voucherApp = ac.getBean(VoucherAppController.class);
		voucherApp.run();
    }
}
