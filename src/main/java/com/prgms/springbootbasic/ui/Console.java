package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.domain.Customer;
import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

	public String initApplication(Menu menu) {
		outputView.initApplication(menu);
		return inputView.enterMenu();
	}

	public void showVoucherList(List<Voucher> vouchers) {
		outputView.showVoucherList(vouchers);
	}

	public void showCustomerList(List<Customer> customers) {
		outputView.showCustomerList(customers);
	}
	
	public String inputVoucherType() {
		outputView.showWhenEntervoucherType();
		return inputView.enterVoucherType();
	}
	
	public Long inputVoucherNumber() {
		outputView.showWhenEnterVoucherNumber();
		return inputView.enterVoucherNumber();
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
