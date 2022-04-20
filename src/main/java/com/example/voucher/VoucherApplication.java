package com.example.voucher;

import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements ApplicationRunner {

	private final Input input;
	private final Output output;

	public VoucherApplication(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		while(true) {
			printCommandPrompt();
			CommandType command = getCommand();
		}
	}

	private void printCommandPrompt() {
		output.printCommandPrompt();
	}

	private CommandType getCommand() {
		return CommandType.of(input.getCommand());
	}

	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
