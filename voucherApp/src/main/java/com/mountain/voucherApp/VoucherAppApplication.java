package com.mountain.voucherApp;

import com.mountain.voucherApp.engine.ExecuteEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class VoucherAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VoucherAppApplication.class, args);
		ExecuteEngine engine = context.getBean(ExecuteEngine.class);
		engine.run();
	}
}
