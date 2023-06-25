package com.prgms.springbootbasic.ui;

import java.util.List;

public class Console {
	
	private final InputView inputView;
	private final OutputView outputView;
	
	public Console(InputView inputView, OutputView outputView) {
		this.inputView = inputView;
		this.outputView = outputView;
	}
	
	public String init() {
		outputView.init();
		return inputView.enterMenu();
	}
	
	public String inputVoucherType() {
		outputView.showWhenEntervoucherType();
		return inputView.enterVoucherType();
	}
	
	public Long inputVoucherNumber() {
		outputView.showWhenEntervoucherNumber();
		return inputView.enterVoucherNumber();
	}
	
	public void showVoucherList(List<String> vouchers) {
		outputView.showVoucherList(vouchers);
	}
	
	public void exit() {
		outputView.exit();
		inputView.close();
		outputView.close();
	}
	
	public void showExceptionMessage(String message) {
		outputView.showExceptionMessage(message);
	}

}
