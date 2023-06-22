package com.example.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.voucher.config.AppConfiguration;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
		CommandLineApplication commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
		commandLineApplication.run();
	}

}
