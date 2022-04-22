package com.example.voucher.io;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter implements Output {

	@Override
	public void printCommandPrompt() {
		System.out.println("=== Voucher Program === \n" +
		                   "Type exit to exit the program.\n" +
				           "Type create to create a new voucher.\n" +
				           "Type list to list all vouchers.");
	}
}
