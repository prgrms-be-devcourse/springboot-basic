package com.example.voucher;

import com.example.voucher.controller.VoucherController;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.dto.VoucherListResponse;
import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

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
						output.printMessage(INVALID_INPUT.name());
						continue;
					}

					int discountAmount = 0;
					try {
						discountAmount = getDiscountAmount();
					} catch (IllegalArgumentException e) {
						output.printMessage(e.getMessage());
						continue;
					}

					try {
						processCreateCommand(voucherType, discountAmount);
					} catch (IllegalArgumentException e) {
						output.printMessage(e.getMessage());
					}
				}

				case LIST: {
					VoucherListResponse voucherListResponse = processListCommand();
					output.printMessage(voucherListResponse);
				}
			}
		}
	}

	private void printCommandPrompt() {
		output.printMessage("=== Voucher Program === \n" +
							"Type exit to exit the program.\n" +
							"Type create to create a new voucher.\n" +
							"Type list to list all vouchers.");
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

	private VoucherListResponse processListCommand() {
		return voucherController.findAll();
	}


	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
