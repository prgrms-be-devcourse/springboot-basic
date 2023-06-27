package com.programmers.application;

import com.programmers.application.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class VoucherApplication {
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
		Controller controller = applicationContext.getBean(Controller.class);
		while(true) {
			controller.process();
		}
	}
}
