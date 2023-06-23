package com.prgrms.spring;

import com.prgrms.spring.infrastructure.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(Application.class);
		var applicationContext = springApplication.run(args);
		applicationContext.getBean(AppRunner.class).run();
	}

}
