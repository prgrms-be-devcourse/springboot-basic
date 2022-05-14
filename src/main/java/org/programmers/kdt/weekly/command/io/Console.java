package org.programmers.kdt.weekly.command.io;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Console {

	private static final Scanner sc = new Scanner(System.in);

	public String getUserInput() {
		return sc.nextLine();
	}

	public void print(String message) {
		System.out.println(message);
	}

	public void printCommandDescription(List<String> description) {
		description.forEach(System.out::println);
	}

	public void programExitMessage() {
		System.out.println("Terminates the program.");
	}

	public void printInfoMessage(InfoMessageType infoMessageType) {
		System.out.println(InfoMessageType.getMessage(infoMessageType));
		System.out.println();
	}

	public void printVoucherSelectMessage() {
		System.out.println("Select a voucher type");
		System.out.println("1. FixedAmountVoucher");
		System.out.println("2. PercentDiscountVoucher");
		System.out.print("Selected : ");
	}

	public void printVoucherDiscountSelectMessage() {
		System.out.println("Please enter the discount value.");
		System.out.print("ENTER : ");
	}
}