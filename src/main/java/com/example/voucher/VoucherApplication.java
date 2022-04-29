package com.example.voucher;

import com.example.voucher.controller.VoucherController;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.dto.VoucherResponse;
import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
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
					printVoucherTypePrompt();
					VoucherType voucherType = getVoucherType();

					if(voucherType == EMPTY) {
						output.printMessage(INVALID_INPUT.name());
						continue;
					}

					printDiscountAmountPrompt();
					int discountAmount = 0;
					try {
						discountAmount = getDiscountAmount();
					} catch (IllegalArgumentException e) {
						output.printMessage(e.getMessage());
						continue;
					}

					// FileUtils에서 예외 넘어올 경우 
					try {
						processCreateCommand(voucherType, discountAmount);
					} catch (IllegalArgumentException e) {
						output.printMessage(e.getMessage());
					}
					break;
				}

				case LIST: {
					try {
						output.printMessage(processListCommand());
					} catch (IllegalArgumentException e) {
						output.printMessage(e.getMessage());
					}
					break;
				}
			}
		}
	}

	private void printCommandPrompt() {
		output.printMessage("=== Voucher Program === \n" +
				            "Type EXIT to exit the program.\n" +
				            "Type CREATE to create a new voucher.\n" +
				            "Type LIST to list all vouchers.");
	}

	private CommandType getCommand() {
		return CommandType.of(input.getString());
	}

	private void printVoucherTypePrompt() {
		output.printMessage("바우처 타입(고정 금액 할인 -> FixedAmountVoucher, 퍼센트 금액 할인 -> PercentDiscountVoucher) 입력: ");
	}

	private VoucherType getVoucherType() {
		return VoucherType.of(input.getString());
	}

	private void printDiscountAmountPrompt() {
		output.printMessage("할인 값 입력:");
	}

	private int getDiscountAmount() {
		return input.getInt();
	}

	private void processCreateCommand(VoucherType voucherType, int discountAmount) {
		voucherController.save(voucherType, discountAmount);
	}

	private List<VoucherResponse> processListCommand() {
		return voucherController.findAll();
	}


	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
