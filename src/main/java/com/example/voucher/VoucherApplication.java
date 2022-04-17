package com.example.voucher;

import com.example.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements ApplicationRunner {

	private final Output output;

	public VoucherApplication(Output output) {
		this.output = output;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		while(true) {
			printCommandPrompt();
		}
	}

	private void printCommandPrompt() {
		output.printCommandPrompt();
	}

	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
