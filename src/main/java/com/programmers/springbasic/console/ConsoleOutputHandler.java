package com.programmers.springbasic.console;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.controller.dto.ListVouchersResponse;

@Component
public class ConsoleOutputHandler {
	public void printMainMenu() {
		String menuPrompt = """
			=== Voucher Program ===
			Type exit to exit the program.
			Type create to create a new voucher.
			Type list to list all vouchers.    
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
		String chooseVoucherType = "Type voucher type : [fixed/percent] => ";
		print(chooseVoucherType);
	}

	public void printVoucherList(List<ListVouchersResponse> vouchers) {
		vouchers.forEach(System.out::println);
	}

	private void print(String menuPrompt) {
		System.out.print(menuPrompt);
	}

}

