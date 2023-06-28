package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.util.VoucherType;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.List;
import java.util.UUID;

public class OutputView {

	private static final String FORMAT_FIXED = "voucher type : %s voucher Id : %s amount : %d";
	private static final String FORMAT_PERCENT = "voucher type : %s voucher Id : %s percent : %d";
	private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
	
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
	
	public void showVoucherList(List<Voucher> vouchers) {
		textTerminal.println("\n=== voucher list ===");
		vouchers.stream()
				.map(v -> {
					VoucherType voucherType = v.getVoucherType();
					UUID voucherId = v.getVoucherId();
					Long number = v.getNumber();
					if (voucherType == VoucherType.FIXED) return String.format(FORMAT_FIXED, voucherType, voucherId, number);
					return String.format(FORMAT_PERCENT, voucherType, voucherId, number);
				})
				.forEach(textTerminal::println);
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
