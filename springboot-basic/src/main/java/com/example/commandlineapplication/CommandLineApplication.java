package com.example.commandlineapplication;

import com.example.commandlineapplication.domain.voucher.controller.VoucherController;
import com.example.commandlineapplication.domain.voucher.service.VoucherFactory;
import com.example.commandlineapplication.global.io.Input;
import com.example.commandlineapplication.global.io.Output;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CommandLineApplication.class, args);
	}

	@Override
	public void run(String... args) {
		VoucherController voucherController = new VoucherController(new Input(), new Output(), new VoucherFactory());
		voucherController.run();
	}
}
