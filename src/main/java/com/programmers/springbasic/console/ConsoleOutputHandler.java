package com.programmers.springbasic.console;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputHandler {
	public void printMainMenu() {
		String menuPrompt = """
			=== Voucher Program ===
			Type exit to exit the program.
			Type create to create a new voucher.
			Type list to list all vouchers.    
			Type blacklist to list all blacklist customers.    
			""";
		print(menuPrompt);
	}

	public void printFixedAmount() {
		String amountPrompt = "Amount : ";
		print(amountPrompt);
	}

	public void printPercentDiscount() {
		String amountPrompt = "Percent : ";
		print(amountPrompt);
	}

	public void printChooseVoucherType() {
		String chooseVoucherType = "Choose voucher type : [fixed/percent] => ";
		print(chooseVoucherType);
	}

	public <T> void printList(List<T> printableList) {
		printableList.forEach(System.out::println);
	}

	private void print(String menuPrompt) {
		System.out.print(menuPrompt);
	}

}
