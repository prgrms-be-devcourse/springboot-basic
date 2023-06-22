package org.devcourse.voucher;

import org.devcourse.voucher.console.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication {

	private final Console console = new Console();

	public static void main(String[] args) {
		SpringApplication.run(org.devcourse.voucher.CommandLineApplication.class, args);
	}

	public void run() {
		console.printMenu();
		String option = console.getOption();
		System.out.println(option);
	}
}


