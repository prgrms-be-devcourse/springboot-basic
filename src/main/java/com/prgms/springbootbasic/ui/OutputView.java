package com.prgms.springbootbasic.ui;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.List;

public class OutputView {
	
	private final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
	
	public void init() {
		textTerminal.println("=== Voucher Application ===");
		textTerminal.println("Type exit to exit program.");
		textTerminal.println("Type create to create a new voucher");
		textTerminal.println("Type list to list all vouchers");
	}
	
	public void showWhenEntervoucherType() {
		textTerminal.println("\nType fixed to create a new fixed voucher.");
		textTerminal.println("Type percent to create a new percent voucher.");
	}
	
	public void showWhenEntervoucherNumber() {
		textTerminal.println("\nNumber of voucher's amount or percent.");
	}
	
	public void showVoucherList(List<String> vouchers) {
		textTerminal.println("\n=== voucher list ===");
		vouchers.stream()
				.forEach(v -> textTerminal.println(v));
		textTerminal.println("");
	}
	
	public void exit() {
		textTerminal.println("\nexit program.");
	}
	
	public void showExceptionMessage(String message) {
		textTerminal.println("\n" + message);
	}
	
	public void close() {
		textTerminal.dispose();
	}
	
}
