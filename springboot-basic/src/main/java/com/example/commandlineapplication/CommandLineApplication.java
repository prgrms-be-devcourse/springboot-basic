package com.example.commandlineapplication;

import com.example.commandlineapplication.config.AppConfig;
import com.example.commandlineapplication.io.Input;
import com.example.commandlineapplication.io.Output;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CommandLineApplication.class, args);
	}

	@Override
	public void run(String... args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(
				AppConfig.class);

		VoucherController voucherController = annotationConfigApplicationContext.getBean(
				VoucherController.class);

		voucherController.run();
	}
}
