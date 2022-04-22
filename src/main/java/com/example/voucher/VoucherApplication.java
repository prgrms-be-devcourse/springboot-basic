package com.example.voucher;

import com.example.voucher.controller.VoucherController;
import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.voucher.VoucherType.EMPTY;


@SpringBootApplication
public class VoucherApplication implements ApplicationRunner {

	private final Input input;
	private final Output output;
	private final VoucherController voucherController;

	public VoucherApplication(Input input, Output output, VoucherController voucherController) {
		this.input = input;
		this.output = output;
		this.voucherController = voucherController;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		while(true) {
			printCommandPrompt();
			CommandType command = getCommand();

			switch (command) {
				case CREATE : {
					VoucherType voucherType = getVoucherType();
					if(voucherType == EMPTY) {
						continue;
					}

					int discountAmount = 0;
					try {
						discountAmount = getDiscountAmount();
					} catch (IllegalArgumentException e) {
						continue;
					}

					processCreateCommand(voucherType, discountAmount);
				}
			}
		}
	}

	private void printCommandPrompt() {
		output.printCommandPrompt();
	}

	private CommandType getCommand() {
		return CommandType.of(input.getString());
	}

	private VoucherType getVoucherType() {
		return VoucherType.of(input.getString());
	}

	private int getDiscountAmount() {
		return input.getInt();
	}

	private void processCreateCommand(VoucherType voucherType, int discountAmount) {
		voucherController.save(voucherType, discountAmount);
	}



	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
