package com.prgrms.w3springboot.io;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import com.prgrms.w3springboot.voucher.Voucher;

@Component
public class OutputConsole implements Output {
	@Override
	public void printInit() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Voucher Program ===");
		sb.append(System.lineSeparator());
		sb.append("Type exit to exit the program.");
		sb.append(System.lineSeparator());
		sb.append("Type create to create a new voucher.");
		sb.append(System.lineSeparator());
		sb.append("Type list to list all vouchers.");
		sb.append(System.lineSeparator());
		sb.append("Type update to update a voucher.");
		sb.append(System.lineSeparator());
		sb.append("Type delete to delete a voucher.");

		System.out.println(sb);
	}

	@Override
	public void printTypeChoice() {
		System.out.print("Which type of voucher do you want to make? [fixed | percent] > ");
	}

	@Override
	public void printDiscountAmountChoice() {
		System.out.print("How much discount do you want to get? > ");
	}

	@Override
	public void printCreatedVoucher(Voucher createdVoucher) {
		StringBuffer sb = new StringBuffer();
		sb.append("Voucher is just created!");
		sb.append(System.lineSeparator());
		sb.append(MessageFormat.format("Voucher info : {0}", createdVoucher));
		System.out.println(sb);
	}

	@Override
	public void printVoucherList(List<Voucher> voucherList) {
		for (var voucher : voucherList) {
			System.out.println(voucher);
		}
	}

	@Override
	public void printExit() {
		System.out.println("Have a Nice Day! :)");
	}

	@Override
	public void printInvalidMessage() {
		System.out.println("Oooops! Please type valid input🙏🏻");
	}

	@Override
	public void printUpdateVoucherChoice() {
		System.out.print("Please type voucher ID to update > ");
	}

	@Override
	public void printUpdateAmountChoice() {
		System.out.print("How much do you want to change it to? > ");
	}

	@Override
	public void printUpdatedVoucher(Voucher updatedVoucher) {
		StringBuffer sb = new StringBuffer();
		sb.append("Voucher is just updated!");
		sb.append(System.lineSeparator());
		sb.append(MessageFormat.format("Voucher info : {0}", updatedVoucher));
		System.out.println(sb);
	}

	@Override
	public void printDeleteChoice() {
		System.out.print("Please type voucher ID to delete > ");
	}

	@Override
	public void printDeleteDone() {
		System.out.println("Delete Done :)");
	}
}
