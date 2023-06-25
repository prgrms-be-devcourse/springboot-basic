package com.prgms.springbootbasic.ui;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class InputView {
	
	private final TextIO textIO = TextIoFactory.getTextIO();
	
	public String enterMenu() {
		return textIO.newStringInputReader()
				.read("Menu");
	}
	
	public String enterVoucherType() {
		return textIO.newStringInputReader()
				.read("VoucherType");
	}
	
	public Long enterVoucherNumber() {
		return textIO.newLongInputReader()
			       .read("VoucherNumber");
	}
	
	public void close() {
		textIO.dispose();
	}
	
}
