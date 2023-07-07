package com.prgms.springbootbasic.ui;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class InputView {
	
	private static final TextIO textIO = TextIoFactory.getTextIO();
	
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

	public String enterUUID() {
		return textIO.newStringInputReader()
				.read("UUID");
	}
	
	public void close() {
		textIO.dispose();
	}
	
}
